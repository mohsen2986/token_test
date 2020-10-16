package com.example.token_test.remote

import com.bumptech.glide.RequestBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import com.example.token_test.*
import com.example.token_test.data.TokenManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.Request
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
        @JvmStatic
        public fun<T> createService(service:Class<T>): T {
            return retrofit.create(service)
        }
        @JvmStatic
        public fun<T> createServiceWithAuth(service: Class<T>, tokenManager: TokenManager): T{
            CostumeAuthentication.tokenManager = tokenManager
            val newClient = client.newBuilder().addInterceptor(object: Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val builder: Request.Builder  = request.newBuilder()
                    if(tokenManager.getToken().accessToken  != "unknown"){
                        builder.addHeader("Authorization" , "Bearer "+ tokenManager.getToken().accessToken +"a")
                    }
                    request = builder.build()
                    return chain.proceed(request)
                }
            })
                    .authenticator(CostumeAuthentication.INSTANCE)
                    .build()
            val newRetrofit :Retrofit = retrofit.newBuilder().client(newClient).build()

            return newRetrofit.create(service)
        }


    }

}