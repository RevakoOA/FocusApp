package com.ostapr.focusapp.core.status_gatherer.initializers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import com.ostapr.focusapp.core.`status-gatherer`.R

private const val GATHERER_NOTIFICATION_ID = 0
private const val GATHERER_NOTIFICATION_CHANNEL_ID = "GathererNotificationChannel"

fun Context.gathererForegroundInfo() = ForegroundInfo(
    GATHERER_NOTIFICATION_ID,
    syncWorkNotification(),
)

/**
 * Notification displayed on lower API levels when sync workers are being
 * run with a foreground service.
 */
private fun Context.syncWorkNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            GATHERER_NOTIFICATION_CHANNEL_ID,
            getString(R.string.gatherer_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = getString(R.string.gatherer_notification_channel_description)
        }
        // Register the channel with the system
        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(
        this,
        GATHERER_NOTIFICATION_CHANNEL_ID,
    )
        .setSmallIcon(R.drawable.airline_stops_24)
        .setContentTitle(getString(R.string.gatherer_notification_title))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
