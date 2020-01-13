package com.example.splashactivity.model.order

import com.google.gson.annotations.SerializedName

class OrderModel {
    @SerializedName("name")
    private var name: String? = null
    @SerializedName("orderCode")
    private var orderCode: String? = null
    @SerializedName("createDay")
    private var createDay: String? = null
}