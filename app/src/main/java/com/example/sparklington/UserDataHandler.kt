package com.example.sparklington

import com.example.sparklington.ServerCommunication.UpdateDonatedGoatNumRequest
import com.example.sparklington.ServerCommunication.UpdateGardenArrayRequest
import com.example.sparklington.ServerCommunication.UpdateGoatAgeRequest
import com.example.sparklington.ServerCommunication.UpdateGrassNumRequest
import com.example.sparklington.ServerCommunication.updateUser

object  UserDataHolder {
    var accessToken: String? = "rnI4WnOusUrZ_MZ_jAV0pq5J663NFzTMAAAAAQoqJZEAAAGRg9tFw1XuKbObXTiX"
    var nickname:String? = null
    var profile_picture_URL:String? = null
    var goat_age: Int = 0 //DB
        set(value){
            if (accessToken!=null){
                updateUser(accessToken!!, UpdateGoatAgeRequest(value))
            }
            field = value
        }
    var hay_num: Int = 0 //DB
        set(value){
            if (accessToken!=null){
                updateUser(accessToken!!, UpdateGrassNumRequest(value))
            }
            field = value
        }
    var garden_array: List<Pair<Int, Int>> = mutableListOf()
        set(value){
            if (accessToken!=null){
                updateUser(accessToken!!, UpdateGardenArrayRequest(value))
            }
            field = value
        }
    var subscription_end_date: String? = null
    var donated_goat_num: Int = 0
        set(value){
            if (accessToken!=null){
                updateUser(accessToken!!, UpdateDonatedGoatNumRequest(value))
            }
            field = value
        }
    fun initialize(goat_age:Int, hay_num:Int, garden_array:List<Pair<Int, Int>>, donated_goat_num:Int){
        this.goat_age = goat_age
        this.hay_num = hay_num
        this.garden_array = garden_array
        this.donated_goat_num = donated_goat_num
    }
}