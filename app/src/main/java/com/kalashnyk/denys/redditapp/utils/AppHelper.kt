package com.kalashnyk.denys.redditapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.kalashnyk.denys.redditapp.App
import com.kalashnyk.denys.redditapp.presentation.activity.main.MainActivity

object AppHelper {

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun injectDependency(activity: Activity) {
        if (activity is MainActivity) {
            (activity.application as App).viewModelComponent!!.inject(activity)
        }
    }

}