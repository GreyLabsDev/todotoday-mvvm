package com.greylabs.todotoday.data

import android.content.Context

private const val PREFS_APP = "app_prefs"

class AppPrefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE)

    fun clearPrefs() {
        sharedPreferences.edit().clear().commit()
    }
}