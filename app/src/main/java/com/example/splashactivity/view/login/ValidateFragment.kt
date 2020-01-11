package com.example.splashactivity.view.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.internal.Utils
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.firebase.FirebaseRequest
import com.example.splashactivity.util.Constant
import com.example.splashactivity.util.InputMethodUtils
import com.example.splashactivity.util.InputMethodUtils.hideSoftKeyboard
import com.example.vinhomes.model.user.UserModel
import com.google.gson.internal.LinkedTreeMap
import com.toankh.roundview.RoundTextView
import kotlinx.android.synthetic.main.fragment_verify_phone.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ValidateFragment : Fragment(),View.OnClickListener {
    lateinit var mActivity :  LoginActivity
    lateinit var mHandler: Handler
    lateinit var button_active : RoundTextView
    lateinit var button_sentAgain : TextView
    lateinit var mTvCodeOtp0 :TextView
    lateinit var mTvCodeOtp1 :TextView
    lateinit var mTvCodeOtp2 :TextView
    lateinit var mTvCodeOtp3 :TextView
    lateinit var mTvCodeOtp4 :TextView
    lateinit var mTvCodeOtp5 :TextView
    lateinit var mEdtOptHidden: EditText
    lateinit var viewInput : View
    lateinit var mTvwPhoneNumber : TextView
    lateinit var mTvwCountDown : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_phone,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_active = view.findViewById(R.id.verify_phone_btn_submit)
        button_sentAgain = view.findViewById(R.id.verify_phone_resend_otp)
        mTvCodeOtp0 = view.findViewById(R.id.verify_phone_otp_code_0)
        mTvCodeOtp1 = view.findViewById(R.id.verify_phone_otp_code_1)
        mTvCodeOtp2 = view.findViewById(R.id.verify_phone_otp_code_2)
        mTvCodeOtp3 = view.findViewById(R.id.verify_phone_otp_code_3)
        mTvCodeOtp4 = view.findViewById(R.id.verify_phone_otp_code_4)
        mTvCodeOtp5 = view.findViewById(R.id.verify_phone_otp_code_5)
        mEdtOptHidden = view.findViewById(R.id.verify_phone_input_otp)
        viewInput = view.findViewById(R.id.verify_phone_otp_code_view)
        mTvwPhoneNumber = view.findViewById(R.id.verify_phone_number)
        mTvwCountDown = view.findViewById(R.id.verify_phone_count_down)
        setViewListeners()
        if (mActivity.userBase != null && !TextUtils.isEmpty(mActivity.userBase!!.userPhone.toString())) {
            mHandler = Handler()
            drawDetail()
            setViewListeners()
        } else {
//            mActivity.replaceFragment(LoginFragment.newInstance(),R.id.container,false)
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as LoginActivity
    }

    fun validete(code :String){
        mActivity.showDialogLoading()
        mActivity.service.validateOtp(mActivity.userBase!!.userPhone.toString(),code)?.enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                mActivity.showDialogError("NOT CONECTION SERVICE")
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()!!.status == 1 ){
                        val t: LinkedTreeMap<*, *> = response.body()!!.content as LinkedTreeMap<*, *>

                        var userFirebase : FirebaseRequest = FirebaseRequest()
                        userFirebase.deviceId = mActivity.getDeviceId()
                        userFirebase.token =mActivity.registerToken()
                        var id :String  = t["id"].toString()
                        val strs = id.split(".").toTypedArray()
                        userFirebase.userId = java.lang.Long.valueOf(strs.get(0))
                        val token :String = t["token"].toString()
                        var user : UserModel = UserModel()
                        user.userPhone =t["userPhone"].toString()
                        user.userFullName =t["userFullName"].toString()
                        mActivity.getPref().edit().putString(Constant.AUTHOCATION,token).apply()
                        mActivity.createFirebase(token,userFirebase);
                        mActivity.getPref().edit().putString(Constant.DATA_USER,mActivity.gson.toJson(user)).apply()
                        mActivity.hideDialogLoading()
                        mActivity.goToHomeActivity()
                    }
                }

                mActivity.hideDialogLoading()

            }

        })
    }

    companion object{
        fun newInstance(): ValidateFragment {
            return ValidateFragment()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.verify_phone_otp_code_view ->{
                InputMethodUtils.showSoftKeyboard(context, mEdtOptHidden);
            }

            R.id.verify_phone_btn_submit ->{
                handlerVerifyOTP();
            }


            R.id.verify_phone_resend_otp->{
                handlerResendOTP();
            }




        }
    }

    private fun setViewListeners() {
        button_active.setOnClickListener(this)
        button_sentAgain.setOnClickListener(this)
        mTvCodeOtp0.setOnClickListener(this)
        mTvCodeOtp1.setOnClickListener(this)
        mTvCodeOtp2.setOnClickListener(this)
        mTvCodeOtp3.setOnClickListener(this)
        mTvCodeOtp4.setOnClickListener(this)
        mTvCodeOtp5.setOnClickListener(this)
        mEdtOptHidden.setOnClickListener(this)
        viewInput.setOnClickListener(this)
        mEdtOptHidden.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                val input = s.toString().trim { it <= ' ' }
                drawOptCode(input)
            }
        });
    }
    private fun drawOptCode(input: String) {
        if (TextUtils.isEmpty(input)) {
            clearOptCode()
        } else {
            val length = input.length
            mTvCodeOtp0.setText(if (length > 0) input[0].toString() else "")
            mTvCodeOtp1.setText(if (length > 1) input[1].toString() else "")
            mTvCodeOtp2.setText(if (length > 2) input[2].toString() else "")
            mTvCodeOtp3.setText(if (length > 3) input[3].toString() else "")
            mTvCodeOtp4.setText(if (length > 4) input[4].toString() else "")
            mTvCodeOtp5.setText(if (length > 5) input[5].toString() else "")
        }
    }

    private fun drawDetail() {
        clearOptCode()
        button_sentAgain.setText(mActivity.fromHtml(getString(R.string.verify_phone_resend_otp)))
        val phoneModel: kotlin.String? = mActivity.userBase!!.userPhone.toString()
        mTvwPhoneNumber.setText(phoneModel)
        startCountDown()
        mEdtOptHidden.requestFocus()
        InputMethodUtils.showSoftKeyboard(context, mEdtOptHidden)
    }

    private fun clearOptCode() {
        mTvCodeOtp0.setText("")
        mTvCodeOtp1.setText("")
        mTvCodeOtp2.setText("")
        mTvCodeOtp3.setText("")
        mTvCodeOtp4.setText("")
        mTvCodeOtp5.setText("")
    }

    private fun startCountDown() {
        mTvwCountDown.setVisibility(View.VISIBLE)
        button_sentAgain.setVisibility(View.GONE)
        stopCountDown()
        var countDownTimer : Long = 60
        mTvwCountDown.setText(
            mActivity.fromHtml(
                String.format(
                    getString( R.string.verify_phone_count_down)
                   ,
                    countDownTimer
                )
            )
        )
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                countDownTimer -= 1
                mTvwCountDown.setText(
                    mActivity.fromHtml(
                        String.format(
                            getString(R.string.verify_phone_count_down),
                            countDownTimer
                        )
                    )
                )
                if (countDownTimer <= 0) {
                    mTvwCountDown.setVisibility(View.GONE)
                    button_sentAgain.setVisibility(View.VISIBLE)
                } else {
                    mHandler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    private fun stopCountDown() {
        if (mHandler != null) {
            try {
                mHandler.removeCallbacksAndMessages(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun handlerVerifyOTP() {
        val code = mEdtOptHidden.text.toString().trim { it <= ' ' }
        if (code == null) {
            mActivity.showSnackBar(R.string.e_601_not_define)
        } else if (TextUtils.isEmpty(code) || code.length != 6) {
            mActivity.showSnackBar(R.string.e_5_need_otp)
        } else {
            hideSoftKeyboard(mActivity)
            mActivity.showDialogLoading()
            validete(code)

        }
    }

    fun handlerResendOTP(){
        mActivity.sentAgainOtp(mActivity.userBase!!.userPhone.toString())
    }

}