package com.ostapr.focusapp.core.data.repositories

import com.ostapr.focusapp.core.common.dispatchers.Dispatcher
import com.ostapr.focusapp.core.common.dispatchers.FocusAppDispatchers.IO
import com.ostapr.focusapp.core.database.dao.FocusDao
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getStatuses(): Flow<List<FocusStatusDetails>> =
        focusAppDao.getStatuses().map { list ->
            list.map { it.convertToCoreStatusDetails() }
        }

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

    override suspend fun removeOldStatuses(
        now: Instant,
        recentDuration: Duration
    ) = withContext(ioDispatcher) {
        val beforeDate = (now - recentDuration).toString()
        focusAppDao.deleteOldStatuses(beforeDate)
    }
}