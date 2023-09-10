package com.ostapr.focusapp.feature.status.model

import android.content.Context
import android.content.pm.PackageManager
import com.ostapr.focusapp.core.domain.DateTimeFormatterUseCase
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import com.ostapr.focusapp.core.model.data.InstalledAppInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/** Convert Core entity to Ui entity. */
class UiStatusDetailsMapper @Inject constructor(
    private val dateFormatter: DateTimeFormatterUseCase,
    @ApplicationContext private val appContext: Context,
    private val forPreview: Boolean = false,
) {

    /** Package manager is not supported in the preview mode. */
    private val packageManager: PackageManager?
        get() = if (!forPreview) appContext.packageManager else null

    fun convertCoreToUi(cores: List<FocusStatusDetails>): List<UiStatusDetails> =
        cores.map(this::convertCoreToUi)

    fun convertCoreToUi(core: FocusStatusDetails): UiStatusDetails {
        return UiStatusDetails(
            core.id,
            dateFormatter(core.dateTime),
            core.installedApps.map(this::convertCoreToUi),
            core.isFocused
        )
    }

    private fun convertCoreToUi(core: InstalledAppInfo) = UiInstalledAppItem(
        name = core.appName,
        image = packageManager?.getApplicationIcon(core.packageName)
    )
}