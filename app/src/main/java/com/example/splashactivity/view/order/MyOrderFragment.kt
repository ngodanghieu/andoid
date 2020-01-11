package com.example.splashactivity.view.order

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.splashactivity.R
import com.example.splashactivity.view.home.HomeActivity
import com.example.splashactivity.view.more.ProfileFragment

class MyOrderFragment: Fragment() {
    lateinit var mActivity : HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_my_orders,container,false)
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): MyOrderFragment {
            return MyOrderFragment()
        }
    }
}