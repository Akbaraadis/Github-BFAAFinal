package com.project.github_api.data.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.project.github_api.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    companion object{
        private const val NOTIF_ID = 1
        private const val RECEIVER_ID = "receiver_1"
        private const val RECEIVER_NAME = "Favorite Reminder"
        private const val FORMAT_TIME = "HH:mm"
        private const val REPEATING_ID = 101
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.project.github_api")
        val delayIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, RECEIVER_ID)
            .setContentIntent(delayIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(context.resources.getString((R.string.app_name)))
            .setContentText("Tambahkan user yang anda sukai dalam daftar favorite user")
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notif = NotificationChannel(RECEIVER_ID, RECEIVER_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(RECEIVER_ID)
            notifManager.createNotificationChannel(notif)
        }

        val notification = builder.build()
        notifManager.notify(NOTIF_ID, notification)
    }

    fun setRepeating(context: Context, type: String, time: String, message: String){
        if(DateInvalid(time, FORMAT_TIME)) return
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java)
        intent.apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }
        val time = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(time[1]))
        calender.set(Calendar.SECOND, 0)
        val delay = PendingIntent.getBroadcast(context, REPEATING_ID, intent, 0)
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.timeInMillis, AlarmManager.INTERVAL_DAY, delay)
        Toast.makeText(context, "Reminder On", Toast.LENGTH_SHORT).show()
    }

    fun offAlarm(context: Context){
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val code = REPEATING_ID
        val delay = PendingIntent.getBroadcast(context, code, intent, 0)
        delay.cancel()
        alarm.cancel(delay)
        Toast.makeText(context, "Reminder off", Toast.LENGTH_SHORT).show()
    }

    private fun DateInvalid(time: String, formatTime: String): Boolean {
        return try {
            val format = SimpleDateFormat(formatTime, Locale.getDefault())
            format.isLenient = false
            format.parse(time)
            false
        } catch (e: ParseException){
            true
        }
    }
}