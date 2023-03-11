package com.example.medicapp.api

import com.example.medicapp.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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

    @PUT("api/updateProfile")
    fun updateCardUser(
        @Header("Authorization") token: String,
        @Body updateUserModel: CreateUserModelInApi
    ): Call<CreateUserModelFromApi>

    @GET("api/news")
    fun getStockAndNews(
        @Header("Authorization") token: String
    ): Call<List<StockAndNewsModel>?>

    @GET("api/catalog")
    fun getCatalog(
        @Header("Authorization") token: String
    ): Call<List<CatalogModel>?>

    @POST("api/avatar")
    fun uploadProfileAvatar(
        @Header("Authorization") token: String,
        @Body imageUri: String
    ): Call<Unit>

    @GET("api/catalog")
    suspend fun getCatalog2(
        @Header("Authorization") token: String
    ): List<CatalogModel>?

}