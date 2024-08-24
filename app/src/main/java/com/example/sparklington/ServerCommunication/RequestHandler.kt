package com.example.sparklington.ServerCommunication


import android.util.Log
import com.example.sparklington.UserDataHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun makeLoginRequest(authToken: String) {
    Log.d("TRY LOGIN", "Hello")
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    val call = apiService.login("Bearer $authToken")
    Log.d("TRY LOGIN", call.toString())
    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                Log.d("LOGIN RESPONSE BODY", response.toString())
                if (loginResponse != null) {
                    UserDataHolder.initialize(
                        loginResponse.user.goat_age,
                        loginResponse.user.grass_num,
                        loginResponse.user.garden_array,
                        loginResponse.user.donated_goat_num
                    )
                    Log.d("LOGIN RESPONSE", "goat_age: ${UserDataHolder.goat_age}")
                }
                else {Log.d("LOGIN RESPONSE", "BODY IS EMPTY")}
            } else {
                Log.d("LOGIN RESPONSE","Request failed with code: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            t.printStackTrace()
            Log.d("LOGIN RESPONSE","Request failed with code: ${t}")
        }
    })
}

fun updateUser(authToken: String, updateUserRequest: UpdateUserRequest) {
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    // Make the PUT request
    val call = apiService.updateUser("Bearer $authToken", updateUserRequest)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val updatedResponse = response.body()
            } else {
                Log.d("UPDATE RESPONSE","Request failed with code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            t.printStackTrace()
            Log.d("UPDATE RESPONSE","Request failed with code: ${t}")
        }
    })
}