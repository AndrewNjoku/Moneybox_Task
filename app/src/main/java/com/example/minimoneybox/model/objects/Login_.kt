package com.example.minimoneybox.model.objects

import com.example.minimoneybox.model.objects.ProductResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass


@RealmClass
open class Login_ : RealmObject() {

    @SerializedName("MoneyboxEndOfTaxYear")
    @Expose
    var moneyboxEndOfTaxYear: String? = null
    @SerializedName("TotalPlanValue")
    @Expose
    var totalPlanValue: Double? = null
    @SerializedName("TotalEarnings")
    @Expose
    var totalEarnings: Double? = null
    @SerializedName("TotalContributionsNet")
    @Expose
    var totalContributionsNet: Int? = null
    @SerializedName("TotalEarningsAsPercentage")
    @Expose
    var totalEarningsAsPercentage: Double? = null
    @SerializedName("ProductResponses")
    @Expose
    var productResponses: RealmList<ProductResponse>? = null

}
