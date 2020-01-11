package com.example.splashactivity.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splashactivity.R
import com.example.splashactivity.model.home.HolderHomeLis
import com.example.splashactivity.model.home.HomeModel
import com.example.splashactivity.view.home.HomeActivity
import com.google.gson.internal.LinkedTreeMap
import layout.AddHomeFragment

class ListHomeAdapter (val items:ArrayList<HolderHomeLis>, val context : HomeActivity, val fl : AddHomeFragment): RecyclerView.Adapter<ListHomeAdapter.ViewHolder>(){
    class ViewHolder (view: View):RecyclerView.ViewHolder(view){
        var name_room : TextView?=null
        init {
            name_room=view.findViewById(R.id.holder_list_home_name)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_list_home,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getrow: Any = this.items.get(position)
        val t: LinkedTreeMap<*, *> = getrow as LinkedTreeMap<*, *>
        val id = t["id"].toString()
        val title : String =  t["title"].toString()
        val price : List<String> = t["priceList"] as List<String>
        holder.name_room!!.setText(title)
        holder.itemView.setOnClickListener({
           var holserList :  HolderHomeLis  = HolderHomeLis()
            val strs = id.split(".").toTypedArray()
            holserList.homeId = java.lang.Long.valueOf(strs.get(0))
            holserList.title = title
            holserList.prices = price
            context.itemHomeSelect =holserList
            fl.drawValueDeafalut()
        })
    }



}