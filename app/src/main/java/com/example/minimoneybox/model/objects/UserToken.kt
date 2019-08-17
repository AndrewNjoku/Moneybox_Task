package com.example.minimoneybox.model.objects

import com.example.minimoneybox.model.objects.Session
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserToken {

    @SerializedName("Session")
    @Expose
    var session: Session? = null

}
