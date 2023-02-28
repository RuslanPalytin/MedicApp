package com.example.medicapp.api

import com.example.medicapp.models.CreateUserModelFromApi
import com.example.medicapp.models.CreateUserModelInApi
import com.example.medicapp.models.MessageModel
import com.example.medicapp.models.TokenModel
import retrofit2.Call
import retrofit2.http.Body
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

    @POST("api/createProfile")
    fun createCardUser(
        @Header("Authorization") token: String,
        @Body createUserModel: CreateUserModelInApi
    ): Call<CreateUserModelFromApi>
}