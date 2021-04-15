package com.project.github_api.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.github_api.R
import com.project.github_api.data.model.Reminder
import com.project.github_api.data.preference.PreferenceReminder
import com.project.github_api.data.receiver.ReminderReceiver
import com.project.github_api.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var alarm: ReminderReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = PreferenceReminder(this)
        if(preference.getReminder().bolReminder){
            binding.settingReminder.isChecked = true
        }
        else{
            binding.settingReminder.isChecked = false
        }

        alarm = ReminderReceiver()

        binding.settingReminder.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                save(true)
                alarm.setRepeating(this, "Repeat", "09:00", "Reminder Favorite")
            }
            else{
                save(false)
                alarm.offAlarm(this)
            }
        }
    }

    private fun save(b: Boolean) {
        val preference = PreferenceReminder(this)
        reminder = Reminder()

        reminder.bolReminder = b
        preference.setReminder(reminder)
    }
}