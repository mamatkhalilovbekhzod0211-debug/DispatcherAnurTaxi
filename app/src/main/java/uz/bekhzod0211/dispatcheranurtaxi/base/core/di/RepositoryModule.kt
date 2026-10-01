package uz.bekhzod0211.dispatcheranurtaxi.base.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.MainRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl.AuthRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl.MainRepositoryImpl
import uz.bekhzod0211.dispatcheranurtaxi.network.AdminDispatcherApi
import uz.bekhzod0211.dispatcheranurtaxi.network.AuthApi

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        setDispatcherApi: AdminDispatcherApi
    ): AuthRepository= AuthRepository(api,setDispatcherApi)



}