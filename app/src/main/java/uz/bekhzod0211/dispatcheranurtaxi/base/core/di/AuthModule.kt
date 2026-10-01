package uz.bekhzod0211.dispatcheranurtaxi.base.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import uz.bekhzod0211.dispatcheranurtaxi.network.AdminDispatcherApi
import uz.bekhzod0211.dispatcheranurtaxi.network.AuthApi


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)


    @Singleton
    @Provides
    fun providesAdminDispatcherApi(retrofit: Retrofit): AdminDispatcherApi =
        retrofit.create(AdminDispatcherApi::class.java)

}