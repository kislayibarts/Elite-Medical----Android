package com.elite.medical.retrofit

import com.elite.medical.utils.endpoints.ConstantsAdmin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantsAdmin.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}