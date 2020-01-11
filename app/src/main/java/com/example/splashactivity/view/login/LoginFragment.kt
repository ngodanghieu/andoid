package com.example.splashactivity.view.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import com.example.splashactivity.R
import com.example.vinhomes.model.user.UserModel

class LoginFragment : Fragment(),View.OnClickListener{


    @BindView(R.id.tv_create_account)
    lateinit var tv_register : TextView
    lateinit var btn_login : Button
    lateinit var edt_name : EditText
    lateinit var edt_pass : EditText
    lateinit var mActivity :  LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_login,container,false);
//        ButterKnife.bind(this,viewRoot)
        tv_register = viewRoot.findViewById(R.id.tv_create_account)
        btn_login = viewRoot.findViewById(R.id.btn_login)
        setViewListeners()
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_name = view.findViewById(R.id.edt_username)
        edt_pass = view.findViewById(R.id.edt_password)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as LoginActivity
    }

    companion object{
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_create_account -> {
                mActivity.navigateToRegister()
            }

            R.id.btn_login -> {
                var user : UserModel = UserModel()
                user.userPhone = edt_name.text.toString()
                user.userHash = edt_pass.text.toString()
                mActivity.checklogin(user)
            }


        }
    }
    private fun setViewListeners() {
        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)

    }
}