package uz.bekhzod0211.dispatcheranurtaxi.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.bekhzod0211.dispatcheranurtaxi.Constants.BASE_URL
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.OrderCreateRequest
import uz.bekhzod0211.dispatcheranurtaxi.data.model.response.main.OrderCreateResponse

interface OrderApiService {
    @POST("api/v1/dispatcher/order") // ← поменяй на свой endpoint
    suspend fun createOrder(
        @Body order: OrderCreateRequest
    ): Response<OrderCreateResponse>
}