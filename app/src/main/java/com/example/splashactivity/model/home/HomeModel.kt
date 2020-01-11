package com.example.splashactivity.model.home

import com.google.gson.annotations.SerializedName

class HomeModel{
    @SerializedName("homeId")
    var homeId :Long? = null
    @SerializedName("title")
    var title :String? = null
    @SerializedName("content")
    var content:String ?= null
    @SerializedName("imageUrl")
    var imageUrl:String ?= null
    @SerializedName("price")
    var price: Double = 0.0
    @SerializedName("createdOn")
    var createdOn: String? = null
    @SerializedName("createdBy")
    var createdBy :String?= null

    constructor()
//    constructor(content :String ,imageUrl :String, price :Double ,homeWorkTimeModels: List<HomeWorkTimeModel>)
}