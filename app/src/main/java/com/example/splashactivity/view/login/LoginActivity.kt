package com.example.splashactivity.view.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.firebase.FirebaseRequest
import com.example.splashactivity.util.Constant
import com.example.splashactivity.view.base.BaseActivity
import com.example.splashactivity.view.home.HomeActivity
import com.example.vinhomes.model.user.UserModel
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    var isCheck : Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var user : String = getPref().getString(Constant.DATA_USER, "")
        if (!TextUtils.isEmpty(user)){
            userBase = gson.fromJson(user,UserModel::class.java)
            toHomeScream(this)
        }else{
            replaceFragment(LoginFragment.newInstance(),R.id.container,true);
        }

    }
    fun navigateToRegister() { // addFragment(RegisterFragment.newInstance(),R.id.container);
        init()
        replaceFragment(RegisterFragment.newInstance(),R.id.container,true)
    }

    fun goToHomeActivity(){
        startActivity(Intent(applicationContext,HomeActivity::class.java))
    }

    fun checklogin(user : UserModel){
        showDialogLoading()
        val call = service.loginUser(user)

        call!!.enqueue(object : Callback<ResponseData?>{
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Toast.makeText(applicationContext, "Hello Javatpoint", Toast.LENGTH_LONG).show()
                Log.d("LOGIN","k data")
                hideDialogLoading()

            }

            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()

                if (response.isSuccessful && response.body() != null){
                    if (response.body()!!.status == 1){
                        val t: LinkedTreeMap<*, *> = response.body()!!.content as LinkedTreeMap<*, *>

                        var userFirebase : FirebaseRequest  = FirebaseRequest()
                        userFirebase.deviceId = getDeviceId()
                        userFirebase.token =registerToken()
                        var id :String  = t["id"].toString()
                        val strs = id.split(".").toTypedArray()
                        userFirebase.userId = java.lang.Long.valueOf(strs.get(0))
                        val token :String = t["token"].toString()
                        user.token =token
                        getPref().edit().putString(Constant.AUTHOCATION,token).apply()
                        createFirebase(token,userFirebase);
                        getPref().edit().putString(Constant.DATA_USER,gson.toJson(user)).apply()
                        hideDialogLoading()
                        toHomeScream(applicationContext)

                    }else if(response.body()!!.status == 2){
                        hideDialogLoading()
                        userBase = user
                        replaceFragment(ValidateFragment.newInstance(),R.id.container,false)
                    }
                    else{
                        hideDialogLoading()
                        showDialogError(response.body()!!.message, "Error",null)
                    }
                }else{
                    hideDialogLoading()
                    showDialogError("Loi trong qua triinh su ly da ta", "Error",null)
                }
            }

        })
    }



}
