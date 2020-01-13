package com.example.splashactivity.model.home

import com.google.gson.annotations.SerializedName

class HomeRequest {
    @SerializedName("id")
     var id: Long? = null
    @SerializedName("content")
     var content: String? = null
    @SerializedName("imageUrl")
     var imageUrl: String? = null
    @SerializedName("price")
     var price = 0.0
    @SerializedName("homeWorkTimeModels")
    var homeWorkTimeModels: List<HomeWorkTIme>? = null
}