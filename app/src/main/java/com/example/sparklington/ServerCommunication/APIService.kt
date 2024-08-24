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
    val goat_age: Int,
    val hay_num: Int,
    val garden_array: List<Pair<Int, Int>>,
    val subscription_end_date: String?,
    val donated_goat_num: Int
)
abstract class UpdateUserRequest()
data class UpdateGoatAgeRequest(val goat_age: Int): UpdateUserRequest()
data class UpdateHayNumRequest(val hay_num: Int): UpdateUserRequest()
data class UpdateGardenArrayRequest(val garden_array: List<Pair<Int, Int>>): UpdateUserRequest()
data class UpdateDonatedGoatNumRequest(val donated_goat_num: Int): UpdateUserRequest()

interface ApiService {

    @GET("api/user/")
    fun login(
        @Header("Authorization") authHeader: String
    ): Call<LoginResponse>

    @PUT("api/user/")
    fun updateUser(
        @Header("Authorization") authHeader: String,
        @Body updateUserRequest: UpdateUserRequest
    ): Call<LoginResponse>
}
