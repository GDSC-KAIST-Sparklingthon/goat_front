package com.example.sparklington.ServerCommunication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

data class LoginResponse(
    val message: String,
    val user: User
)

data class User(
    val id: String,
    val goat_age: Int,
    val grass_num: Int,
    val garden_array: List<Pair<Int, Int>>,
    val subscription_end_date: String?,
    val donated_goat_num: Int
)

interface ApiService {

    @GET("api/user/")
    fun login(
        @Header("Authorization") authHeader: String
    ): Call<LoginResponse>

    @PUT("api/user/")
    fun updateUser(
        @Header("Authorization") authHeader: String,
        @Body updateUserRequest: User
    ): Call<LoginResponse>
}
