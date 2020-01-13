package com.example.splashactivity.retrofit2

import com.example.homes.model.home.ResponseData
import com.example.splashactivity.model.firebase.FirebaseRequest
import com.example.splashactivity.model.home.HomeRequest
import com.example.vinhomes.model.user.UserModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @POST("api/user/register")
    fun register(@Body userRequest : UserModel): Call<ResponseData>?

    @GET("api/user/validateOTPCode")
    fun validateOtp(@Query("phone") phone :String,@Query("otp") otp :String): Call<ResponseData>?

    @POST("api/user/sentAgainOtp")
    fun sentAgainOtp(@Query("phone") phone :String): Call<ResponseData>?

    @POST("api/user/login")
    fun loginUser(@Body userRequest: UserModel?) : Call<ResponseData?>?

    @GET("api/home/get-all-home")
    fun getDataHome() : Call<ResponseData?>?

//    @Headers( "Content-Type: application/json;charset=UTF-8")
    @POST("api/firebase/create-userFcm")
    fun firebase(@Header("Authorization") token  :String ,@Body firebase : FirebaseRequest) : Call<ResponseData?>?


    @GET("api/home/get-all-by-user/{idUser}")
    fun getDataByUser(@Header("Authorization") token  :String ,@Query("idUser") id :Long) : Call<ResponseData?>?


    @GET("api/order/get-all-order-new-by-user")
    fun getOrderNewByOwnner(@Header("Authorization") token  :String ,@Query("idUser") id :Long) : Call<ResponseData?>?


    @POST("api/home/create-home")
    fun creaateHome(@Header("Authorization") token  :String ,@Body home :HomeRequest) : Call<ResponseData?>?

    @Multipart
    @POST("upload")
    fun uploadphoto(@Header("Authorization") token  :String ,@Part photo: MultipartBody.Part?): Call<String>?

    @POST("api/order/change-Status-Order")
    fun changeStatus(@Header("Authorization") token  :String ,@Query("orderCode") code :String) : Call<ResponseData?>?

    @DELETE("api/order/delete-Order")
    fun removeOrder(@Header("Authorization") token  :String ,@Query("orderCode") code :String) : Call<ResponseData?>?

}