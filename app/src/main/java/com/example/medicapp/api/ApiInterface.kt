package com.example.medicapp.api

import com.example.medicapp.models.MessageModel
import com.example.medicapp.models.TokenModel
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/sendCode")
    fun sendCode(
        @Header("email") email: String
    ): Call<MessageModel>

    @POST("api/signin")
    fun signIn(
        @Header("email") email: String,
        @Header("code") code: String
    ): Call<TokenModel>
}