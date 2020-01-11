package com.example.splashactivity.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HomeModel
import com.example.splashactivity.view.home.HomeActivity
import com.example.splashactivity.view.home.HomeFragment
import com.example.splashactivity.view.login.LoginFragment
import com.google.gson.internal.LinkedTreeMap
import com.squareup.picasso.Picasso


class HomeAdapter (val items:ArrayList<HomeModel>, val context : HomeActivity): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    class ViewHolder (view: View):RecyclerView.ViewHolder(view){

        var imgView: ImageView? = null
        var name_room : TextView?=null
        var tv_price : TextView?=null

        init {
            imgView=view.findViewById(R.id.img_image_home)
            name_room=view.findViewById(R.id.tv_title)
//            tv_price=view.findViewById(R.id.tvprice)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_home,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getrow: Any = this.items.get(position)
        val t: LinkedTreeMap<*, *> = getrow as LinkedTreeMap<*, *>
        val img = t["imageUrl"].toString()
        val title : String =  t["title"].toString()
        val price : String =  t["price"].toString()
        Picasso.get().load(img).into(holder.imgView);
        holder.name_room!!.setText(title)
//        holder.tv_price!!.setText(price)
        holder.itemView.setOnClickListener({
            Toast.makeText(context,title,Toast.LENGTH_LONG).show()
            context.replaceFragment(LoginFragment.newInstance(),R.id.home_container,false)
        })
    }



}