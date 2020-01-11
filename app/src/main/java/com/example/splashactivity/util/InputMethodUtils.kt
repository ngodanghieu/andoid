package com.example.splashactivity.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object InputMethodUtils {
    private val TAG = InputMethodUtils::class.java.simpleName
    fun hideSoftKeyboard(context: Context, editText: EditText?) {

        if (editText == null) return
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm != null && activity.currentFocus != null) {
                imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showSoftKeyboard(context: Context?, editText: EditText?) {
        if (context == null || editText == null) {
            return
        }
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) { // imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            // imm.showSoftInput(this.editText,InputMethodManager.SHOW_FORCED);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyboardWhenTouch(activity: Activity?, view: View?) {
        if (view == null || activity == null) {
            return
        }
        if (view !is EditText) {
            view.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent? ->
                hideSoftKeyboard(activity)
                false
            })
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyboardWhenTouch(activity, innerView)
            }
        }
    }
}
