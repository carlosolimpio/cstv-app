package com.carlosolimpio.cstv.di

import com.carlosolimpio.cstv.data.mainlist.MainListRepositoryImpl
import com.carlosolimpio.cstv.data.mainlist.remote.MainListService
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val PANDASCORE_API_BASE_URL = "https://api.pandascore.co/csgo/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PANDASCORE_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMainListService(retrofit: Retrofit): MainListService {
        return retrofit.create(MainListService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainListRepository(apiService: MainListService): MainListRepository {
        return MainListRepositoryImpl(apiService)
    }
}
