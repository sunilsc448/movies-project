package com.example.assignment.di.module

import com.example.assignment.data.api.API_KEY
import com.example.assignment.data.api.API_KEY_KEY
import com.example.assignment.data.api.ApiService
import com.example.assignment.data.api.BASE_URL
import com.example.assignment.di.scope.MainComponentScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    @MainComponentScope
    fun getInterceptor():Interceptor{
        return Interceptor { chain ->
            val newUrl = chain.request().url().newBuilder().addQueryParameter(API_KEY_KEY, API_KEY).build()
            val newRequest = chain.request().newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @MainComponentScope
    fun getOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    @Provides
    @MainComponentScope
    fun getRetrofit(client:OkHttpClient):Retrofit{
        return Retrofit.Builder().
        baseUrl(BASE_URL).
        client(client).addConverterFactory(GsonConverterFactory.create()).
        build()
    }

    @Provides
    @MainComponentScope
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}