package uz.bekhzod0211.dispatcheranurtaxi.utils

import android.util.Log
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = tokenProvider.getToken()

        if (!token.isNullOrEmpty()) {
            requestBuilder
                .addHeader("Authorization", "Bearer $token")

//            requestBuilder.header("Authorization",token)
        }
        val response = chain.proceed(requestBuilder.build())
        if (response.code == 401){
            tokenProvider.setUnAuthorized()
        }
        return response
    }
}