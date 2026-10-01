package uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl

import android.util.Log
import jakarta.inject.Inject
import retrofit2.Response
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.login.LoginRequest
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.LoginResponse
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.SetDispatcherResponse
import uz.bekhzod0211.dispatcheranurtaxi.network.AdminDispatcherApi
import uz.bekhzod0211.dispatcheranurtaxi.network.AuthApi

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val adminDispatcherApi: AdminDispatcherApi
) {
    suspend fun login(phone: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(
            adminSecret = "royaltaxi-admin-2024",
            fullName = "Admin Adminov",
            password = password,
            phone = phone
        )
        val response = api.login(request)
        Log.d("TTT","login")

        return response
    }


    suspend fun setDispatcher(id: Int): Response<SetDispatcherResponse>{
        return adminDispatcherApi.setDispatcher(user_id = id)
    }
}