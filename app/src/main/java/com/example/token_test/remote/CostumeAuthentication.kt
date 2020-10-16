package com.example.token_test.remote

import com.example.token_test.data.TokenManager
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*

class CostumeAuthentication private constructor(
        val tokenManager: TokenManager
    ): Authenticator{

    private object MakeInstance {
        lateinit var INSTANCE : CostumeAuthentication
        fun makeInstance(tokenManager: TokenManager){
            INSTANCE = CostumeAuthentication(tokenManager)
        }
    }

    companion object{
        lateinit var tokenManager: TokenManager

        @JvmStatic
        val INSTANCE by lazy{
            MakeInstance.makeInstance(tokenManager)
            MakeInstance.INSTANCE
        }
    }
    override fun authenticate(route: Route?, response: Response): Request? {
        if(responseCount(response) > 2){
            return null
        }
        val token = tokenManager.getToken()

        val service: ApiInterface = RetrofitBuilder.createService(ApiInterface::class.java)
        return runBlocking(IO){
            when (val callBack = service.refresh(token.refreshToken)) {
                is NetworkResponse.Success -> {
                    tokenManager.saveToken(callBack.body)
                    return@runBlocking response.request.newBuilder().header("Authorization" , "Bearer "+ callBack.body.accessToken).build()
                }
                is NetworkResponse.ServerError -> {

                }
            }
            return@runBlocking null
        }
    }
    private fun responseCount(response: Response):Int{
        var count = 1
        var response: Response? = response
        while (response != null){
            count++
            response = response.priorResponse
        }
        return count
    }

}