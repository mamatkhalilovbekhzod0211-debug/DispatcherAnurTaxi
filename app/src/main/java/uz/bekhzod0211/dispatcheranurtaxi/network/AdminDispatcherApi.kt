package uz.bekhzod0211.dispatcheranurtaxi.network

import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.login.SetDispatcherResponse

interface AdminDispatcherApi {
    @PUT("api/v1/admin/users/{user_id}/set-dispatcher")
    suspend fun setDispatcher(@Path("user_id") user_id: Int): Response<SetDispatcherResponse>
}