package com.example.splashactivity.view.base

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.splashactivity.retrofit2.ApiService
import com.example.splashactivity.retrofit2.RestClient
import com.example.splashactivity.view.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.model.firebase.FirebaseRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.Settings.Secure;
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import com.example.splashactivity.util.Constant
import com.example.splashactivity.view.login.LoginActivity
import com.example.vinhomes.model.user.UserModel
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson

open class BaseActivity : AppCompatActivity(){
    private var mRes: Resources? = null
    private lateinit var mPref: SharedPreferences
    private val doubleBackToExitPressedOnce = false
    private var mHandler: Handler? = null
    private  var loadingDialog: MaterialDialog ? = null
    private  var errorDialog: MaterialDialog ? = null
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mFragmentTransaction: FragmentTransaction
     lateinit var gson: Gson
    var userBase : UserModel? = null

    val service : ApiService = RestClient.retrofitInstance!!.create(ApiService::class.java)
    val serviceImage: ApiService = RestClient.retrofitInstanceImage!!.create(ApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentManager = getSupportFragmentManager()
        mHandler = Handler()
        mPref = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        gson = Gson()

    }

    fun init(){
        mFragmentManager = getSupportFragmentManager()
        mRes = getResources()
        mHandler = Handler()
    }

    fun getPref():SharedPreferences {
        return mPref;
    }



    fun replaceFragment(
        fragment: Fragment?,
        fragmentParentId: Int,
        isAddToBackStack: Boolean
    ) {
        mFragmentTransaction = mFragmentManager.beginTransaction()
        if (fragment != null) {
            mFragmentTransaction.replace(fragmentParentId, fragment)
        }
        if (isAddToBackStack) {
            mFragmentTransaction.addToBackStack(null)
        } /*else {
            mFragmentTransaction.disallowAddToBackStack();
        }*/
        mFragmentTransaction.commit()
    }

    fun replaceFragment(
        fragment: Fragment?,
        fragmentParentId: Int,
        tag: String?
    ) {
        mFragmentTransaction = mFragmentManager!!.beginTransaction()
        if (fragment != null) {
            mFragmentTransaction!!.replace(fragmentParentId, fragment)
        }
        if (!TextUtils.isEmpty(tag)) {
            mFragmentTransaction!!.addToBackStack(tag)
        }
        mFragmentTransaction!!.commit()
    }


    fun toHomeScream(conten :Context){
        startActivity(Intent(conten, HomeActivity::class.java))
    }

    fun toLoginScream(conten :Context){
        startActivity(Intent(conten, LoginActivity::class.java))
    }

    open fun showSnackBar(resMsg: Int) {
        showSnackBar(mRes!!.getString(resMsg))
    }

    open fun showSnackBar(msg: String?) {
        val snackbar =
            Snackbar.make(window.decorView.rootView, msg!!, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(mRes!!.getColor(R.color.white))
        snackbar.view.setBackgroundColor(mRes!!.getColor(R.color.darker_gray))
        snackbar.show()
    }

    open fun showToast(resMsg: Int) {
        showToast(mRes!!.getString(resMsg))
    }

    open fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    open fun showDialogLoading() {
        showDialogLoading(
            "Xin cho",
            "loading"
        )
    }

    open fun showDialogLoading(resTitle: Int, resMsg: Int) {
        showDialogLoading(mRes!!.getString(resTitle), mRes!!.getString(resMsg))
    }

    open fun showDialogLoading(title: String?, msg: String?) {
        synchronized(FragmentActivity.AUDIO_SERVICE) {
            if (loadingDialog == null || !loadingDialog!!.isShowing()) {
                loadingDialog = MaterialDialog.Builder(this@BaseActivity) //.title(title)
                    .content(msg!!)
                    .progress(true, 0)
                    .cancelable(false)
                    .backgroundColorRes(R.color.transparent)
                    .progressIndeterminateStyle(false).build()
            }
            loadingDialog!!.show()
        }
    }

    open fun hideDialogLoading() {
        synchronized(FragmentActivity.AUDIO_SERVICE) {
            if (loadingDialog != null && loadingDialog!!.isShowing()) {
                loadingDialog!!.dismiss()
//                loadingDialog = null
            }
        }
    }

    open fun showDialogError(msgRes: Int) {
        showDialogError(mRes!!.getString(msgRes))
    }

    open fun showDialogConfirm(
        msg: String?,
        callback: MaterialDialog.SingleButtonCallback?
    ) {
        showDialogError(msg, null, callback)
    }

    open fun showDialogError(msg: String?) {
        showDialogError(msg, null, null)
    }

    open fun showDialogError(
        msg: String?,
        title: String?,
        callback: MaterialDialog.SingleButtonCallback?
    ) {
        synchronized(FragmentActivity.AUDIO_SERVICE) {
            if (errorDialog != null && errorDialog!!.isShowing()) {
                try {
                    errorDialog!!.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (callback == null) errorDialog = MaterialDialog.Builder(this@BaseActivity)
                .title(title.toString())
                .content(msg.toString())
                .cancelable(true)
                .positiveText("close")
                .build() else errorDialog = MaterialDialog.Builder(this@BaseActivity)
                .title(title!!)
                .content(msg!!)
                .cancelable(true)
                .positiveText(mRes!!.getString(R.string.ok))
                .negativeText(mRes!!.getString(R.string.cancel))
                .onPositive(callback)
                .build()
            errorDialog!!.show()
        }
    }

    fun createFirebase(token :String,data : FirebaseRequest){
        showDialogLoading()
        service.firebase(token,data)!!.enqueue(object : Callback<ResponseData?> {
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()?.status == 1){
                        Log.d("FRIEBASE", "====================OK========================")
                    }else{
                        Log.d("FRIEBASE", "====================ERROR========================")
                    }
                }
                hideDialogLoading()
            }

            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                hideDialogLoading()
            }
        })
    }

    fun sentAgainOtp(phone :String){
        showDialogLoading()
        service.sentAgainOtp(phone)!!.enqueue(object : Callback<ResponseData?> {
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()?.status == 1){
                        Log.d("SENT OK", "====================OK========================")
                    }else{
                        Log.d("SENT OK", "====================ERROR========================")
                    }
                }
                hideDialogLoading()
            }

            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                hideDialogLoading()
            }

        })
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String? {
        return try {
            Secure.getString(this.getContentResolver(), Secure.ANDROID_ID)
        } catch (e: Exception) {
            e.printStackTrace()
            "-"
        }
    }

    fun registerToken() :String {
        return FirebaseInstanceId.getInstance().token.toString();
    }

    fun fromHtml(source: String?): Spanned? {
        if (TextUtils.isEmpty(source))

            return SpannableString("")
        else
            return  Html.fromHtml(source)

    }

}