package com.example.minimoneybox.model.objects

import com.example.minimoneybox.model.objects.HideAccounts
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Personalisation: RealmObject() {

    @SerializedName("QuickAddDeposit")
    @Expose
    var quickAddDeposit: QuickAddDeposits? = null
    @SerializedName("HideAccounts")
    @Expose
    var hideAccounts: HideAccounts? = null

}
