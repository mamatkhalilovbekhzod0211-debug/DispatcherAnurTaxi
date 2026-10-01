package uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import retrofit2.Response
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.MainRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.OrderCreateRequest
import uz.bekhzod0211.dispatcheranurtaxi.network.OrderApiService

class MainRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    val api: OrderApiService
) : MainRepository {
    override suspend fun createOrder(order: OrderCreateRequest): Boolean {
//        val networkHelper = NetworkHelper(context)
     /*   val retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(createGson())
            .client(provideOkHttpClient(provideChuckerInterceptor(context)))
            .build()


        val api = retrofit.create(OrderApiService::class.java)*/

        val response = api.createOrder(order)
        return if (response.isSuccessful) {
            Toast.makeText(context, "Buyurtma muvaffaqaiyatli yaratildi", Toast.LENGTH_LONG).show()
            true
        } else {
            Toast.makeText(context, "Buyurtma xatolik:${response.errorBody()}", Toast.LENGTH_LONG).show()
//            Response.error(response.code(), response.errorBody()!!)
            false
        }
    }
/*
    fun provideChuckerInterceptor(
        context: Context
    ): ChuckerInterceptor =
        ChuckerInterceptor
            .Builder(context)
            .build()

    fun provideOkHttpClient(
        interceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .build()
    }
    private fun createGson(): GsonConverterFactory {
        return GsonConverterFactory.create(Gson().newBuilder().setLenient().create())

    }*/
}