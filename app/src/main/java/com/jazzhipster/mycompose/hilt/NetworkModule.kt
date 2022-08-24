package com.jazzhipster.mycompose.hilt

import com.jazzhipster.mycompose.remote.mock.HomePageServiceImp
import com.jazzhipster.mycompose.remote.service.HomePageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    @Provides
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideHomePageService(
        retrofit: Retrofit
    ): HomePageService =
        HomePageServiceImp()
        //retrofit.create(HomePageService::class.java)

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}