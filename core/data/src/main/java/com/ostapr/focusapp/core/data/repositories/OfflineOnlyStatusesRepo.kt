package com.ostapr.focusapp.core.data.repositories

import com.ostapr.focusapp.core.common.dispatchers.Dispatcher
import com.ostapr.focusapp.core.common.dispatchers.FocusAppDispatchers
import com.ostapr.focusapp.core.common.dispatchers.FocusAppDispatchers.IO
import com.ostapr.focusapp.core.database.dao.FocusDao
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.relations.StatusDetails
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlin.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineOnlyStatusesRepo @Inject constructor(
    private val focusAppDao: FocusDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : StatusesRepository {
    override suspend fun addStatus(statusDetails: FocusStatusDetails) = withContext(ioDispatcher) {
        val statusId =
            focusAppDao.insertStatus(StatusEntity.createFromCoreStatusDetails(statusDetails))

        val apps =
            statusDetails.installedApps.map { InstalledAppEntity.createFromCoreInstalledApp(it) }
        val appIds = focusAppDao.insertInstalledApps(apps)

        val crossRefs =
            appIds.map { appId -> StatusAppCrossRef.createFrom(statusId = statusId, appId = appId) }
        focusAppDao.insertStatusWithApp(crossRefs)
    }

    override suspend fun removeOldAndFetchRecentStatuses(
        now: Instant,
        recentDuration: Duration
    ): List<FocusStatusDetails> = withContext(ioDispatcher) {
        focusAppDao.deleteOldStatuses(now, recentDuration)

        val statusesDetails = focusAppDao.getStatuses(now, recentDuration)

        statusesDetails.map { it.convertToCoreStatusDetails() }
    }
}