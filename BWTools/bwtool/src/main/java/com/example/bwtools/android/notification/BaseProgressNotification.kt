package com.example.bwtools.android.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat


abstract class BaseProgressNotification(context: Context) : BaseNotification(context) {

    /**
     * 노티피케이션 프로그래스 업데이트 및 보내기
     */
    fun sendDownloadNotification(max: Int, progress: Int) {
        Log.d(javaClass.simpleName, "sendDownloadNotification() max = $max, progress = $progress")

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationBuilderData.notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationCompat.Builder(context, notificationBuilderTarget.channelId)
        else
            NotificationCompat.Builder(context).setPriority(Notification.PRIORITY_DEFAULT)

        builder!!
            .setSmallIcon(notificationBuilderData.smallImage)
            .setContentTitle(String.format("${notificationBuilderData.title} %s", "${(progress.toDouble() / max * 100).toInt()}%"))
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (max <= progress) {
            builder!!.setContentText(notificationBuilderData.content)
        }else{
            builder!!.setProgress(max, progress, false)
        }

        notificationManager.notify(notificationBuilderData.notificationId, builder!!.build())
    }

}