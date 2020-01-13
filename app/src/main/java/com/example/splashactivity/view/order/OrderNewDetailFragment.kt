package com.example.splashactivity.view.order

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.order.OrderModel
import com.example.splashactivity.util.Constant
import com.example.splashactivity.view.Adapter.OrderNewAdapter
import com.example.splashactivity.view.home.HomeActivity
import com.example.vinhomes.model.user.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderNewDetailFragment : Fragment(), View.OnClickListener {
    lateinit var mActivity : HomeActivity

    lateinit var name : TextView
    lateinit var ordercode : TextView
    lateinit var nameUser : TextView
    lateinit var phone : TextView
    lateinit var note : TextView
    lateinit var createDay : TextView

    lateinit var btn_xn :Button
    lateinit var btn_huy :Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_order_detail,container,false)
        var user : String = mActivity.getPref().getString(Constant.DATA_USER, "")
        if (!TextUtils.isEmpty(user)){
            mActivity.userBase = mActivity.gson.fromJson(user, UserModel::class.java)
        }
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.holder_my_order_name)
        ordercode = view.findViewById(R.id.holder_my_order_do_value)
        nameUser = view.findViewById(R.id.holder_name_value)
        phone = view.findViewById(R.id.holder_phone_value)
        note = view.findViewById(R.id.holder_note_value)
        createDay = view.findViewById(R.id.holder_my_order_time_value)
        btn_xn =view.findViewById(R.id.btn_xacnhan)
        btn_huy =view.findViewById(R.id.btn_huy)
        btn_xn.setOnClickListener(this)
        btn_huy.setOnClickListener(this)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        lateinit var orderCode : String
        fun newInstance(code : String): OrderNewDetailFragment {
            orderCode = code
            return OrderNewDetailFragment()
        }
    }

    fun xacnhan(){
        mActivity.showDialogLoading()
        val token : String = mActivity.getPref().getString(Constant.AUTHOCATION, "")
        mActivity.service.changeStatus(token, orderCode)?.enqueue(object :
            Callback<ResponseData?> {
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Log.d("HOME ADD","k data")
                mActivity.hideDialogLoading()
            }
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if(response.isSuccessful && response.body() != null){
//                    initAdapter(response.body()!!.content as ArrayList<OrderModel>)\
                    mActivity.hideDialogLoading()
                    mActivity.showSnackBar("Thanh cong")
                }
                mActivity.hideDialogLoading()
            }
        })
    }

    fun huy(){
        mActivity.showDialogLoading()
        val token : String = mActivity.getPref().getString(Constant.AUTHOCATION, "")
        mActivity.service.removeOrder(token, orderCode)?.enqueue(object :
            Callback<ResponseData?> {
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Log.d("HOME ADD","k data")
                mActivity.hideDialogLoading()
            }
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if(response.isSuccessful && response.body() != null){
//                    initAdapter(response.body()!!.content as ArrayList<OrderModel>)\
                    mActivity.hideDialogLoading()
                    mActivity.showSnackBar("Thanh cong")
                }
                mActivity.hideDialogLoading()
            }
        })
    }

//    fun initAdapter(list : ArrayList<OrderModel>) {
//        val homeListAdapter = OrderNewAdapter(list,mActivity)
//        recyclerViewOrderNew.layoutManager= GridLayoutManager(mActivity,1)
//        recyclerViewOrderNew.adapter=homeListAdapter
//
//    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_xacnhan ->{
                xacnhan()
            }

            R.id.btn_huy ->{
                huy()
                mActivity.replaceFragment(OrderManager.newInstance(),R.id.home_container,false)
            }
        }
    }
}