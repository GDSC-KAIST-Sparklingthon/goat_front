package com.example.sparklington

object  UserDataHolder {
    var accessToken: String? = null
    var nickname:String? = null
    var profile_picture_URL:String? = null
    var goat_age: Int = 0
    var grass_num: Int = 0
    var garden_array: List<Pair<Int, Int>> = mutableListOf()
    var subscription_end_date: String? = null
    var donated_goat_num: Int = 0
}