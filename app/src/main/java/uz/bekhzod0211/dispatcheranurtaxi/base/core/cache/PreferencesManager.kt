package uz.devmi.usale.core.cache

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.bekhzod0211.dispatcheranurtaxi.base.core.utils.Constants.DEFAULT_STRING

class PreferencesManager(
    @ApplicationContext private val context: Context
) {
    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            context
        )
    }

    fun clearAll() {
        preferences.edit().clear().apply()
    }

    var token by PreferencesDelegate(preferences, TOKEN, DEFAULT_STRING)



    private var preferencesManagerChangeListener: (() -> Unit)? = null
    private val onSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key -> preferencesManagerChangeListener?.invoke() }

    init {
        preferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    fun setDataChangeListener(listener: (() -> Unit)? = null) {
        preferencesManagerChangeListener = listener
    }

    companion object {
        private const val URL = "url"
        private const val TOKEN = "TOKEN"
    }
}