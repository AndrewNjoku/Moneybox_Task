package com.example.minimoneybox.model.objects


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ProductResponse: RealmObject(){

    @SerializedName("Id")
    @Expose
    var id: Int? = null
    @SerializedName("PlanValue")
    @Expose
    var planValue: Double? = null
    @SerializedName("Moneybox")
    @Expose
    var moneybox: Int? = null
    @SerializedName("SubscriptionAmount")
    @Expose
    var subscriptionAmount: Int? = null
    @SerializedName("TotalFees")
    @Expose
    var totalFees: Double? = null
    @SerializedName("IsSelected")
    @Expose
    var isSelected: Boolean? = null
    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean? = null
    @SerializedName("Product")
    @Expose
    var product: Product? = null
    @SerializedName("InvestorAccount")
    @Expose
    var investorAccount: InvestorAccount? = null
    @SerializedName("Personalisation")
    @Expose
    var personalisation: Personalisation? = null

}