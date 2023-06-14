package com.example.assignmentproject.di

import android.content.Context
import androidx.room.Room
import com.example.assignmentproject.localdb.AppDatabase
import com.example.assignmentproject.localdb.DataDao
import com.example.assignmentproject.localdb.ScoreDao
import com.example.assignmentproject.localdb.entities.DataEntity
import com.example.assignmentproject.network.ApiService
import com.example.assignmentproject.repositories.DataRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/13/2023 at 3:14 PM
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://mocki.io"

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database",
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDao {
        return database.scoreDao()
    }

    @Provides
    fun provideEntity() = DataEntity()

    @Provides
    fun provideDataRepository(apiService: ApiService, dataDao: DataDao,scoreDao: ScoreDao): DataRepository {
        return DataRepository(apiService, dataDao, scoreDao)
    }
}
