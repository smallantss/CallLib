package com.witted.net

import com.google.gson.Gson
import retrofit2.Converter

class CustomGsonConverterFactory(val gson: Gson) : Converter.Factory() {

    companion object {

        fun create(): CustomGsonConverterFactory {
            return create(Gson())
        }

        fun create(gson: Gson): CustomGsonConverterFactory {
            return CustomGsonConverterFactory(gson)
        }
    }

}