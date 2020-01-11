package com.example.splashactivity.view.login

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import butterknife.internal.Utils
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.User
import com.example.vinhomes.model.user.UserModel
import okhttp3.internal.Util
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment(),View.OnClickListener {
    lateinit var mActivity :  LoginActivity

    lateinit var button_register : Button
    lateinit var edit_pass : EditText
    lateinit var edit_phone : EditText
    lateinit var edit_name : EditText
    lateinit var btn_login : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_register = view.findViewById(R.id.reg_button_join_us)
        edit_name = view.findViewById(R.id.reg_name_et)
        edit_pass = view.findViewById(R.id.reg_password_et)
        edit_phone = view.findViewById(R.id.reg_phone_et)
        btn_login = view.findViewById(R.id.fragment_resgister_login)
        setViewListeners()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as LoginActivity
    }

    fun register(userModel: UserModel){
        mActivity.showDialogLoading()
        mActivity.service.register(userModel)?.enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful && response.body() != null){
                    if (response.body()!!.status == 1 ){
                        mActivity.userBase = userModel
                        mActivity.hideDialogLoading()
                        mActivity.replaceFragment(ValidateFragment.newInstance(),R.id.container,false)
                    }else{
                        Toast.makeText(context,response.body()!!.message,Toast.LENGTH_LONG).show()
                        mActivity.hideDialogLoading()
                        mActivity.showDialogError(response.body()!!.message, "Error",null)
                    }
                }
                mActivity.hideDialogLoading()
            }

        })
    }

    companion object{
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.reg_button_join_us ->{
                val phone :String ?=edit_phone.text.toString()
                val name :String ? = edit_name.text.toString()
                val pass :String ? = edit_pass.text.toString()
                if (TextUtils.isEmpty(phone)){

                }else if (TextUtils.isEmpty(name)){

                }else if (TextUtils.isEmpty(pass)){

                }

                var user : UserModel = UserModel()
                user.userFullName = name
                user.userPhone = phone
                user.userHash = pass

                register(user)
            }
            R.id.fragment_resgister_login ->{
                mActivity.replaceFragment(LoginFragment.newInstance(),R.id.container,false)
            }
        }
    }

    private fun setViewListeners() {
        button_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }
}