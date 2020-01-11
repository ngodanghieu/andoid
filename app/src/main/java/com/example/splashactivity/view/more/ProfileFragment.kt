package com.example.splashactivity.view.more

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.splashactivity.R
import com.example.splashactivity.util.Constant
import com.example.splashactivity.view.home.HomeActivity
import com.example.splashactivity.view.home.HomeFragmentMenu
import com.example.splashactivity.view.login.LoginActivity
import com.example.splashactivity.view.login.LoginFragment

class ProfileFragment : Fragment(),View.OnClickListener {
    lateinit var mActivity : HomeActivity
    lateinit var btn_logout : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_profile,container,false)
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout = view.findViewById(R.id.profile_logout)
        setEvenOnclick()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.profile_logout -> {
                mActivity.getPref().edit().remove(Constant.DATA_USER).apply()
                startActivity(Intent(mActivity,LoginActivity::class.java))
            }
        }
    }

    fun setEvenOnclick(){
        btn_logout.setOnClickListener(this)
    }
}