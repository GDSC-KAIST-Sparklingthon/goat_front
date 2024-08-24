package com.example.sparklington.ServerCommunication

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import java.lang.reflect.Type

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
sealed class UpdateUserRequest()
data class UpdateGoatAgeRequest(val goat_age: Int): UpdateUserRequest()
data class UpdateHayNumRequest(val hay_num: Int): UpdateUserRequest()
data class UpdateGardenArrayRequest(val garden_array: List<Pair<Int, Int>>): UpdateUserRequest()
data class UpdateDonatedGoatNumRequest(val donated_goat_num: Int): UpdateUserRequest()

class UpdateUserRequestAdapter : JsonSerializer<UpdateUserRequest>,
    JsonDeserializer<UpdateUserRequest> {
    override fun serialize(src: UpdateUserRequest?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return context?.serialize(src)!!
    }
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UpdateUserRequest {
        val jsonObject = json?.asJsonObject
        return when {
            jsonObject?.has("goat_age") == true -> context?.deserialize(json, UpdateGoatAgeRequest::class.java)!!
            jsonObject?.has("hay_num") == true -> context?.deserialize(json, UpdateHayNumRequest::class.java)!!
            jsonObject?.has("garden_array") == true -> context?.deserialize(json, UpdateGardenArrayRequest::class.java)!!
            jsonObject?.has("donated_goat_num") == true -> context?.deserialize(json, UpdateDonatedGoatNumRequest::class.java)!!
            else -> throw IllegalArgumentException("Unknown type of UpdateUserRequest")
        }
    }
}


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
