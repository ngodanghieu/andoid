package com.example.splashactivity.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HomeModel
import com.example.splashactivity.model.order.OrderModel
import com.example.splashactivity.view.home.HomeActivity
import com.example.splashactivity.view.login.LoginFragment
import com.example.splashactivity.view.order.OrderNewDetailFragment
import com.example.splashactivity.viewmodel.HomeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.squareup.picasso.Picasso

class OrderNewAdapter (val items:ArrayList<OrderModel>, val context : HomeActivity): RecyclerView.Adapter<OrderNewAdapter.ViewHolder>(){
    class ViewHolder (view: View): RecyclerView.ViewHolder(view){

        var imgView: ImageView? = null
        var name_room : TextView?=null
        var tv_date : TextView?=null
        var tv_status : TextView?=null
        var tv_ordercode : TextView?=null


        init {
            imgView=view.findViewById(R.id.holder_my_order_status)
            name_room=view.findViewById(R.id.holder_my_order_name)
            tv_date=view.findViewById(R.id.holder_my_order_time_value)
            tv_status=view.findViewById(R.id.holder_my_order_status_value)
            tv_ordercode=view.findViewById(R.id.holder_my_order_do_value)
//

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_my_order,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getrow: Any = this.items.get(position)
        val t: LinkedTreeMap<*, *> = getrow as LinkedTreeMap<*, *>
        val name = t["name"].toString()
        val ordercode : String =  t["orderCode"].toString()
        val createday : String =  t["createDay"].toString()

        holder.name_room!!.setText(name)
        holder.tv_ordercode!!.setText(ordercode)
        holder.tv_status!!.setText("Dang cho duyet")
        holder.tv_date!!.setText(createday)
        holder.itemView.setOnClickListener({
            context.replaceFragment(OrderNewDetailFragment.newInstance(ordercode), R.id.home_container,false)
        })
    }



}