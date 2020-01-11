package com.example.splashactivity.view.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homes.model.home.ResponseData
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HomeModel
import com.example.splashactivity.view.login.LoginActivity
import com.example.splashactivity.view.login.LoginFragment
import com.example.splashactivity.viewmodel.HomeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    lateinit var mActivity : HomeActivity
    lateinit var recyclerView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot : View = inflater.inflate(R.layout.fragment_home,container,false)
        recyclerView = viewRoot.findViewById(R.id.home_recyclerView)
        getDataHome()
        return viewRoot;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    fun getDataHome(){
        mActivity.service.getDataHome()?.enqueue(object : Callback<ResponseData?> {
            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Toast.makeText(context, "Hello Javatpoint", Toast.LENGTH_LONG).show()
                Log.d("HOMEFRAGMENT","k data")
            }
            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if(response.isSuccessful && response.body() != null){
                    initAdapter(response.body()!!.content as ArrayList<HomeModel>)

                }
            }
        })
    }

    fun initAdapter(list : ArrayList<HomeModel>) {
        val homeListAdapter = HomeAdapter(list,mActivity)
        recyclerView.layoutManager= GridLayoutManager(mActivity,1)
        recyclerView.adapter=homeListAdapter

    }

//    fun  parseArray(json: String): HomeModel? {
//        val gson = GsonBuilder().create()
////        return gson.fromJson<HomeModel>(json, HomeModel)
//    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as HomeActivity
    }

    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}