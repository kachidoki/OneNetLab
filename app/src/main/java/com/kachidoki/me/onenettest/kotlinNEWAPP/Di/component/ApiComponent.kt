package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.component

import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.ApiModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceDetilModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceListModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.KotlinApp
import dagger.Component

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Component(modules = arrayOf(ApiModule::class))
interface ApiComponent{

    fun inject(app:KotlinApp)

    fun plus(module: DeviceListModule):DeviceListComponent
    fun plus(module: DeviceDetilModule):DeviceDetilComponent
}