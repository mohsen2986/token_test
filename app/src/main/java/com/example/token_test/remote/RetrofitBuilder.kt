package com.example.token_test.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import com.example.token_test.*
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder{
    companion object{
        private  val  URL = "http://www.paarandco.ir/server_api/api/"
        @JvmStatic
        private val client: OkHttpClient by lazy {
            val builder = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        var request = chain.request()

                        val builder = request.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Connection", "close")

                        request = builder.build()
                        return chain.proceed(request)
                    }
                })
//            if(BuildConfig.DEBUG)
            builder.addNetworkInterceptor(StethoInterceptor())
            builder.build()
        }
        @JvmStatic
        private val retrofit: Retrofit by lazy{
            Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        public fun<T> createService(service:Class<T>): T {
            return retrofit.create(service)
        }


    }

}