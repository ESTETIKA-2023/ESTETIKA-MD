package com.bangkit23.estetika.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data"    ) var data    : Data?   = Data(),
    @SerializedName("message" ) var message : String? = null
) {
    data class Data (

        @SerializedName("email"    ) var email    : String? = null,
        @SerializedName("user_id"  ) var userId   : String? = null,
        @SerializedName("username" ) var username : String? = null

    )
}


