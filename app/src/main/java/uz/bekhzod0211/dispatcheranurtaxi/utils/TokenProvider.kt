package uz.bekhzod0211.dispatcheranurtaxi.utils

import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import uz.devmi.usale.core.cache.PreferencesManager

@Singleton
class TokenProvider @Inject constructor(
    private val prefs: PreferencesManager
) {
    //    private var token: String? = null
    private val _logOutEvents = MutableSharedFlow<Unit>(
        0, 1, BufferOverflow.DROP_OLDEST
    )
    val logOutEvents = _logOutEvents.asSharedFlow()

    fun setToken(newToken: String) {
        prefs.token = newToken
    }

    fun getToken(): String {
        return prefs.token
    }

    fun setUnAuthorized(){
        prefs.token = ""
        _logOutEvents.tryEmit(Unit)
    }
}