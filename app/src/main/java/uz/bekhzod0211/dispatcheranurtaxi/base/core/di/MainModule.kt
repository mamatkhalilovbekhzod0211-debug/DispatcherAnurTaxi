package uz.bekhzod0211.dispatcheranurtaxi.base.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import uz.bekhzod0211.dispatcheranurtaxi.network.OrderApiService

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class MainModule {

    @Singleton
    @Provides
    fun provideOrderApiService(retrofit: Retrofit): OrderApiService
     =retrofit.create(OrderApiService::class.java)
}