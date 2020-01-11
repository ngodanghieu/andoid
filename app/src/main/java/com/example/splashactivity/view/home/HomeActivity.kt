package com.example.splashactivity.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HolderHomeLis
import com.example.splashactivity.util.Utils
import com.example.splashactivity.view.base.BaseActivity
import java.util.*


class HomeActivity : BaseActivity(), View.OnClickListener {

    lateinit var btn_menu : AppCompatImageView
    lateinit var maintLeft : View
    lateinit var mDrawerLayout : DrawerLayout
    lateinit var actionBar : ActionBarDrawerToggle
    lateinit var itemHomeSelect : HolderHomeLis
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_menu = findViewById(R.id.ab_menu)
        maintLeft = findViewById(R.id.main_left_menu)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        setOnclick()
        replaceFragment(HomeFragment.newInstance(),R.id.home_container,true);
        replaceFragment(HomeFragmentMenu.newInstance(),R.id.main_left_menu,false);
        actionBar = ActionBarDrawerToggle(this, mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(actionBar)

    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.ab_menu->{
                hidenOrOpenDrawer()
            }
        }
    }
    fun hidenOrOpenDrawer(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
    fun setOnclick(){
        btn_menu.setOnClickListener(this)
    }

//    private fun showDropDownCities() {
//        val spinnerItems: ArrayList<SpinnerItem> = Utils.convertCityToSpinner(cities)
//        DialogSpinner(
//            this,
//            null,
//            true,
//            spinnerItems,
//            object : DialogListener<SpinnerItem?>() {
//                fun onPositive(result: SpinnerItem) {
//                    try {
//                        val city: CityModel = cities.get(result.getPosition())
//                        if (currentCity != null && currentCity.getId() === city.getId()) {
//                        } else {
//                            currentCity = city
//                            currentDistrict = null
//                            districts = null
//                            drawCityAndDistrict()
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//
//                fun onNegative() {}
//            }).show()
//    }




}
