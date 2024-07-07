package com.example.task.model

import com.google.gson.annotations.SerializedName

data class LoginRes (
    @SerializedName("authorized")
    val authorized : String,

    @SerializedName("token")
    val token : String,

    @SerializedName("host")
    val host :String,

    @SerializedName("email")
    val email :String,

    @SerializedName("ok")
    val ok :String,
)




