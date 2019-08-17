package com.example.minimoneybox.model.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Payment {

    @SerializedName("Amount")
    @Expose
    var Amount: Int? = null
    @SerializedName("InvestorProductId")
    @Expose
    var InvestorProductId: Int? = null


}