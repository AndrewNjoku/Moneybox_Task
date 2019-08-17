package com.example.minimoneybox.model.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class HideAccounts: RealmObject() {

    @SerializedName("Enabled")
    @Expose
    var enabled: Boolean? = null
    @SerializedName("IsHidden")
    @Expose
    var isHidden: Boolean? = null
    @SerializedName("Sequence")
    @Expose
    var sequence: Int? = null

}
