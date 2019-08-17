package com.example.minimoneybox.model.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass


@RealmClass
open class QuickAddDeposits : RealmObject() {

    @SerializedName("Amount")
    @Expose
    var amount: Int? = null

}
