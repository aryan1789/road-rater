package com.roadrater.di

import com.roadrater.preferences.AppearancePreferences
import com.roadrater.preferences.GeneralPreferences
import com.roadrater.preferences.preference.AndroidPreferenceStore
import com.roadrater.preferences.preference.PreferenceStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val PreferencesModule = module {
    single { AndroidPreferenceStore(androidContext()) }.bind(PreferenceStore::class)

    singleOf(::AppearancePreferences)
    singleOf(::GeneralPreferences)
}
