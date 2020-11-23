package com.witted.net

import com.witted.base.BuildConfig
import com.witted.utils.TIME_LIMIT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit

class RequestUtil private constructor() {

    companion object {

        @Volatile
        private var instance: RequestUtil? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RequestUtil().also {
                instance = it
            }
        }
    }

    val retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(getNetworkInterceptor())
            .addInterceptor(getLogInterceptor())
            .connectTimeout(TIME_LIMIT, TimeUnit.SECONDS)
            .readTimeout(TIME_LIMIT, TimeUnit.SECONDS)
            .writeTimeout(TIME_LIMIT, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("http://" + "SpUtils.getIp()")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    //涉及到一些公用参数的拼接放在这里
    private fun getNetworkInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
//                val oldHost = request.url.host
//                val oldPort = request.url.port
////                logd("old:${oldHost},${oldPort}")
//                try {
//                    val ip = SpUtils.getIp()
//                    val url = URL("http://$ip")
//                    val newHost = url.host
//                    val newPort = if (url.port == -1) {
//                        80
//                    } else {
//                        url.port
//                    }
////                    logd("new:${newHost},${newPort}")
//                    if (oldHost != newHost || oldPort != newPort) {
//                        val httpUrl = request.url.newBuilder().host(newHost).port(newPort).build()
//                        request = request.newBuilder().url(httpUrl).build()
//                    }
//                    return chain.proceed(request)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
                return chain.proceed(request)
            }
        }
    }

    //普通的日志打印
    private fun getLogInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}