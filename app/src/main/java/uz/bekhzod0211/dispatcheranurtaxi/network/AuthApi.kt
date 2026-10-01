package uz.bekhzod0211.dispatcheranurtaxi.network
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.login.LoginRequest
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.LoginResponse

interface AuthApi {
    @POST("api/v1/auth/admin/signup")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}