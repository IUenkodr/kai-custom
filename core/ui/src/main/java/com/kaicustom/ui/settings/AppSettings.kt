package com.kaicustom.ui.settings

import android.content.Context
import android.content.SharedPreferences

object AppSettings {
    private const val PREFS_NAME = "kai_custom_settings"
    private const val KEY_SDCARD_ACCESS = "enable_sdcard_access"

    fun setSdCardAccess(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_SDCARD_ACCESS, enabled).apply()
    }

    fun isSdCardAccessEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_SDCARD_ACCESS, false)
    }
}
