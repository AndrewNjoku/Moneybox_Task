package com.example.minimoneybox.model.objects


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass


@RealmClass
open class Product: RealmObject() {

    @SerializedName("Id")
    @Expose
    var id: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("CategoryType")
    @Expose
    var categoryType: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("FriendlyName")
    @Expose
    var friendlyName: String? = null
    @SerializedName("CanWithdraw")
    @Expose
    var canWithdraw: Boolean? = null
    @SerializedName("ProductHexCode")
    @Expose
    var productHexCode: String? = null
    @SerializedName("AnnualLimit")
    @Expose
    var annualLimit: Int? = null
    @SerializedName("DepositLimit")
    @Expose
    var depositLimit: Int? = null
    @SerializedName("Lisa")
    @Expose
    private var lisa: Lisa? = null

    fun getLisa(): Lisa? {
        return lisa
    }

    fun setLisa(lisa: Lisa) {
        this.lisa = lisa
    }

}