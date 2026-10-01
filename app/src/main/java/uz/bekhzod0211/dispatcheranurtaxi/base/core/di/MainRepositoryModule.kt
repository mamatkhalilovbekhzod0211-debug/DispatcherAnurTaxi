package uz.bekhzod0211.dispatcheranurtaxi.base.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.MainRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl.MainRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(impl: MainRepositoryImpl): MainRepository
}