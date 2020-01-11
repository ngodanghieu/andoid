package com.example.splashactivity.model.home

import com.google.gson.annotations.SerializedName

class HolderHomeLis {
    @SerializedName("id")
    var homeId :Long? = null
    @SerializedName("title")
    var title :String? = null
    @SerializedName("priceList")
    var prices : List<String>? = null
}