package com.example.splashactivity.view.order

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.splashactivity.R
import com.example.splashactivity.view.home.HomeActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.model.home.HolderHomeLis
import com.example.splashactivity.model.home.HomeModel
import com.example.splashactivity.model.order.OrderModel
import com.example.splashactivity.util.Constant
import com.example.splashactivity.view.Adapter.OrderNewAdapter
import com.example.splashactivity.viewmodel.HomeAdapter
import com.example.vinhomes.model.user.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderManager : Fragment() {
    lateinit var mActivity : HomeActivity
    lateinit var recyclerViewOrderNew : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_new_order,container,false)
        var user : String = mActivity.getPref().getString(Constant.DATA_USER, "")
        if (!TextUtils.isEmpty(user)){
            mActivity.userBase = mActivity.gson.fromJson(user, UserModel::class.java)
        }
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewOrderNew = view.findViewById(R.id.order_new_recycler_view)
        getData()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): OrderManager {
            return OrderManager()
        }
    }

    fun getData(){

        val token : String = mActivity.getPref().getString(Constant.AUTHOCATION, "")
        mActivity.service.getOrderNewByOwnner(token,mActivity.userBase!!.idUser)?.enqueue(object : Callback<ResponseData?> {
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Log.d("HOME ADD","k data")
            }
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if(response.isSuccessful && response.body() != null){
                    initAdapter(response.body()!!.content as ArrayList<OrderModel>)
                }
            }
        })
    }

    fun initAdapter(list : ArrayList<OrderModel>) {
        val homeListAdapter = OrderNewAdapter(list,mActivity)
        recyclerViewOrderNew.layoutManager= GridLayoutManager(mActivity,1)
        recyclerViewOrderNew.adapter=homeListAdapter

    }
}