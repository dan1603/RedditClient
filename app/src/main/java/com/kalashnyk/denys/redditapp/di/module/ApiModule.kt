package com.kalashnyk.denys.redditapp.di.module

import com.kalashnyk.denys.redditapp.di.scope.ApiScope
import com.kalashnyk.denys.redditapp.repository.api.RedditApi
import com.kalashnyk.denys.redditapp.repository.server.ServerCommunicator
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {


    @Provides
    @ApiScope
    fun provideServerCommunicator(api: RedditApi) : ServerCommunicator {
        return ServerCommunicator(api)
    }

    @Provides
    @ApiScope
    fun provideApiService(retrofit: Retrofit): RedditApi {
        return retrofit.create<RedditApi>(RedditApi::class.java)
    }

    @Provides
    @ApiScope
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder
                .baseUrl(API_URL)
                .build()
    }

    @Provides
    @ApiScope
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val builder = OkHttpClient.Builder()
                .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)


        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
    }

    companion object {
        private val API_URL = "https://api.reddit.com/"
    }

}