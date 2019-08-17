package com.example.minimoneybox.model.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login {

    @SerializedName("Email")
    @Expose
    var email: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
    @SerializedName("Idfa")
    @Expose
    var idfa: String? = null

}