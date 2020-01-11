//package com.example.splashactivity.view.base
//
//import android.view.View
//import android.view.View.OnLongClickListener
//import androidx.recyclerview.widget.RecyclerView
//import com.example.splashactivity.view.dialog.RecyclerClickListener
//
//abstract class BaseViewHolder : RecyclerView.ViewHolder {
//
//    private lateinit var mRecyclerClickListener: RecyclerClickListener<T>
//    private val itemView: View? = null
//
//    constructor(itemView : View) : super(itemView) {
//
//    }
//
//    open fun setRecyclerClickListener(mRecyclerClickListener: RecyclerClickListener?) {
//        this.mRecyclerClickListener = mRecyclerClickListener
//    }
//
//    open fun getRecyclerClickListener(): RecyclerClickListener? {
//        return mRecyclerClickListener
//    }
//
//    abstract fun setElement(obj: Any?)
//
//    open fun getItemView(): View? {
//        return itemView
//    }
//
//    open fun setViewClick(position: Int, `object`: Any?) {
//        itemView.setOnClickListener(View.OnClickListener { v: View? ->
//            if (mRecyclerClickListener != null) {
//                mRecyclerClickListener.onClick(v, position, `object`)
//            }
//        })
//        itemView.setOnLongClickListener(OnLongClickListener { v: View? ->
//            if (mRecyclerClickListener != null) {
//                mRecyclerClickListener.onLongClick(v, position, `object`)
//            }
//            false
//        })
//    }
//}
