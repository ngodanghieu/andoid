package com.example.splashactivity.view.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.splashactivity.R
import com.example.splashactivity.view.more.AboutFragment
import com.example.splashactivity.view.more.ContactFragment
import com.example.splashactivity.view.more.MyFavoriteFragment
import com.example.splashactivity.view.more.ProfileFragment
import com.example.splashactivity.view.order.MyOrderFragment
import com.example.splashactivity.view.order.OrderManager
import layout.AddHomeFragment


class HomeFragmentMenu : Fragment(),View.OnClickListener {
    lateinit var mActivity : HomeActivity

    lateinit var btn_home : View
    lateinit var btn_profile : View
    lateinit var btn_my_order : View
    lateinit var btn_my_favation : View
    lateinit var btn_contact : View
    lateinit var btn_about : View
    lateinit var btn_setting : View
    lateinit var btn_new_order : View
    lateinit var menu_profile_image : ImageView
    lateinit var menu_profile_name : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home_menu,container,false)
        btn_home = view.findViewById(R.id.menu_home)
        btn_contact = view.findViewById(R.id.menu_contact_us)
        btn_about = view.findViewById(R.id.menu_about)
        btn_my_favation = view.findViewById(R.id.menu_favorite)
        btn_my_order = view.findViewById(R.id.menu_my_orders)
        btn_profile = view.findViewById(R.id.menu_profile)
        btn_setting = view.findViewById(R.id.menu_setting)
        menu_profile_image = view.findViewById(R.id.menu_profile_image)
        menu_profile_name = view.findViewById(R.id.menu_profile_name)
        btn_new_order = view.findViewById(R.id.new_order)
        setViewsListener();
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): HomeFragmentMenu {
            return HomeFragmentMenu()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.menu_home -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(HomeFragment.newInstance(),R.id.home_container,false)
            }
            R.id.menu_contact_us -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(ContactFragment.newInstance(),R.id.home_container,false)
            }
            R.id.menu_about -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(AboutFragment.newInstance(),R.id.home_container,false)
            }
            R.id.menu_favorite -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(MyFavoriteFragment.newInstance(),R.id.home_container,false)
            }
            R.id.menu_my_orders -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(MyOrderFragment.newInstance(),R.id.home_container,false)
            }

            R.id.menu_profile -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(ProfileFragment.newInstance(), R.id.home_container,false)
            }

            R.id.menu_setting -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(AddHomeFragment.newInstance(), R.id.home_container,false)
            }

            R.id.new_order -> {
                mActivity.hidenOrOpenDrawer()
                mActivity.replaceFragment(OrderManager.newInstance(), R.id.home_container,false)
            }
        }
    }


    fun setViewsListener(){
        btn_home.setOnClickListener(this)
        btn_contact.setOnClickListener(this)
        btn_about.setOnClickListener(this)
        btn_my_favation.setOnClickListener(this)
        btn_my_order.setOnClickListener(this)
        btn_profile.setOnClickListener(this)
        btn_setting.setOnClickListener(this)
        menu_profile_image.setOnClickListener(this)
        menu_profile_name.setOnClickListener(this)
        btn_new_order.setOnClickListener(this)
    }


}