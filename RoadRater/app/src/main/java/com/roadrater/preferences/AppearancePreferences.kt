package com.roadrater.preferences

import android.os.Build
import com.roadrater.preferences.preference.PreferenceStore
import com.roadrater.preferences.preference.getEnum
import com.roadrater.ui.theme.DarkMode

class AppearancePreferences(preferenceStore: PreferenceStore) {
    val darkMode = preferenceStore.getEnum("dark_mode", DarkMode.System)
    val materialYou = preferenceStore.getBoolean("material_you", Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
}