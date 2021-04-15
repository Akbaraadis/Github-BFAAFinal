package com.project.github_api.data.preference

import android.content.Context
import com.project.github_api.data.model.Reminder

class PreferenceReminder(context: Context) {
    companion object{
        const val NAME_PREFERENCE = "pref_reminder"
        private const val REMINDER = "bolReminder"
    }

    private val preference = context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder){
        val alarm = preference.edit()
        alarm.putBoolean(REMINDER, value.bolReminder)
        alarm.apply()
    }

    fun getReminder(): Reminder{
        val alarm = Reminder()
        alarm.bolReminder = preference.getBoolean(REMINDER, false)
        return alarm
    }
}