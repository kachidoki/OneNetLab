package com.kachidoki.me.onenettest.kotlinNEWAPP.app

import android.content.Context
import android.widget.Toast

/**
 * Created by Kachidoki on 2017/6/1.
 */
fun Context.getApiComponent() = KotlinApp.INSTANCE.apiComponent

fun Context.toast(mag:String,length:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,mag,length).show()
}