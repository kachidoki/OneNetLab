package com.kachidoki.me.onenettest.kotlinNEWAPP.app

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.Toast
import com.kachidoki.me.onenettest.R

/**
 * Created by Kachidoki on 2017/6/1.
 */
fun Context.getApiComponent() = KotlinApp.INSTANCE.apiComponent

fun Context.toast(mag:String,length:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,mag,length).show()
}

@BindingAdapter("stream_image")
fun streamImage(imageView: ImageView, id: String?) {
    when(id){
        "运行时间"->{ imageView.setImageResource(R.drawable.runtime) }
        "运行状态"->{ imageView.setImageResource(R.drawable.workstatue) }
        else ->{ imageView.setImageResource(R.drawable.temp) }
    }
}
