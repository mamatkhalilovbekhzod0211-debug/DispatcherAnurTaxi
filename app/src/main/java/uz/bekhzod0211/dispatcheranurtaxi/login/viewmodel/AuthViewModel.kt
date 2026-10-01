package uz.bekhzod0211.dispatcheranurtaxi.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl.AuthRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.LoginResponse
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.SetDispatcherResponse
import uz.bekhzod0211.dispatcheranurtaxi.utils.TokenProvider
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    private val _navigation = MutableSharedFlow<AuthNavEvent>()
    val navigation = _navigation
    init {
        tokenProvider.getToken()?.let {
            if (it.isNotEmpty()) {
                viewModelScope.launch {
                    _navigation.emit(AuthNavEvent.GoToMainActivity)
                }
            }
        }

    }


    fun login(phone: String, password: String) {
        viewModelScope.launch {

            _loginState.value = LoginUiState.Loading
            try {
                val response: Response<LoginResponse> = repository.login(phone, password)
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.token

                    tokenProvider.setToken(token!!)
                    Log.d("TOKEN", "token:$token")
                    _loginState.value = LoginUiState.Success(response.body()!!)
//                    _navigation.emit(AuthNavEvent.GoToMainActivity)
                    setDispatcher(response.body()?.user?.id!!)
                } else {
                    _loginState.value =
                        LoginUiState.Error("Ошибка: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error("Ошибка: ${e.localizedMessage}")
            }
        }
    }


    fun setDispatcher(user_id: Int){
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            try {
                val response: Response<SetDispatcherResponse> = repository.setDispatcher(user_id)
                if (response.isSuccessful && response.body() != null) {

                    _navigation.emit(AuthNavEvent.GoToMainActivity)
                } else {
                    _loginState.value =
                        LoginUiState.Error("Ошибка: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error("Ошибка: ${e.localizedMessage}")
            }
        }
    }

    sealed class AuthNavEvent {
        object GoToMainActivity : AuthNavEvent()
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data: LoginResponse) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}