package uz.bekhzod0211.dispatcheranurtaxi.base.core.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.bekhzod0211.dispatcheranurtaxi.Constants.BASE_URL
import uz.bekhzod0211.dispatcheranurtaxi.network.AuthApi
import uz.bekhzod0211.dispatcheranurtaxi.utils.AuthInterceptor
import uz.bekhzod0211.dispatcheranurtaxi.utils.TokenProvider
import uz.devmi.usale.core.cache.PreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor = authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun provideTokenProvider(
        preferencesManager: PreferencesManager
    ): TokenProvider = TokenProvider(
        preferencesManager
    )

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext
        context: Context): SharedPreferences =
        context.getSharedPreferences(
            "DispatcherAnurTaxi",
            Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesPreferencesManager(
        @ApplicationContext context: Context): PreferencesManager =
        PreferencesManager(context)

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext
        context: Context): ChuckerInterceptor =
        ChuckerInterceptor
            .Builder(context)
            .build()




}