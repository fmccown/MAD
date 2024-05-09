package com.zybooks.countdowntimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zybooks.countdowntimer.ui.timerText
import kotlinx.coroutines.delay

const val CHANNEL_ID_TIMER = "channel_timer"
const val NOTIFICATION_ID = 0
const val KEY_MILLIS_REMAINING = "com.zybooks.countdowntimer.MILLIS_LEFT"

class TimerWorker(context: Context, parameters: WorkerParameters) :
   CoroutineWorker(context, parameters) {

   private val notificationManager =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

   override suspend fun doWork(): Result {
      // Get remaining milliseconds from MainActivity
      var remainingMillis = inputData.getLong(KEY_MILLIS_REMAINING, 0)

      // Can't continue without remaining time
      if (remainingMillis == 0L) {
         return Result.failure()
      }

      // Create notification channel for all notifications
      createTimerNotificationChannel()

      // Post notifications every second until no time remains
      while (remainingMillis > 0) {
         postTimerNotification(timerText(remainingMillis))
         delay(1000)
         remainingMillis -= 1000
      }

      // Post final notification
      postTimerNotification("Timer is finished!")

      return Result.success()
   }

   private fun createTimerNotificationChannel() {
      // Notification channels only available on O and above
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val channel = NotificationChannel(CHANNEL_ID_TIMER, "Timer Channel",
            NotificationManager.IMPORTANCE_LOW)
         channel.description = "Displays how much time is left"

         // Register channel
         notificationManager.createNotificationChannel(channel)
      }
   }

   private fun postTimerNotification(text: String) {
      // Create notification with various properties
      val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_TIMER)
         .setSmallIcon(android.R.drawable.ic_dialog_info)
         .setContentTitle(applicationContext.getString(R.string.app_name))
         .setContentText(text)
         .setPriority(NotificationCompat.PRIORITY_LOW)
         .build()

      // Post notification
      if (notificationManager.areNotificationsEnabled()) {
         notificationManager.notify(NOTIFICATION_ID, notification)
      }

      Log.d("TimerWorker", text)
   }
}