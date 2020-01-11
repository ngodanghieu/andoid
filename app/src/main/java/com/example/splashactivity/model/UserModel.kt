package com.example.vinhomes.model.user


import com.google.gson.annotations.SerializedName


class UserModel{
    @SerializedName("id")
    var idUser : Long  =0
    @SerializedName("userFullName")
    var userFullName: String? = null
    @SerializedName("userPhone")
    var userPhone: String? = null
    @SerializedName("userEmail")
    var userEmail: String? = null
    @SerializedName("nameRole")
    var nameRole: String? = null
    @SerializedName("userHash")
    var userHash:String? = null
    @SerializedName("token")
    var token:String? = null


    constructor()



}