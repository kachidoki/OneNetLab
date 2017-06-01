package com.kachidoki.me.onenettest.kotlinNEWAPP.app

import android.app.Application
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.component.ApiComponent
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.component.DaggerApiComponent
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.ApiModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.AppModule
import javax.inject.Inject

/**
 * Created by Kachidoki on 2017/6/1.
 */
class KotlinApp : Application(){
    init {
        INSTANCE=this
    }

    @Inject lateinit var apiComponent:ApiComponent

    override fun onCreate() {
        super.onCreate()

        DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(this))
                .build()
                .inject(this)

    }

    companion object{
        lateinit var INSTANCE: KotlinApp
    }
}