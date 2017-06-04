package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.component

import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceDetilModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceDetilActivity
import dagger.Subcomponent

/**
 * Created by Kachidoki on 2017/6/4.
 */
@Subcomponent(modules = arrayOf(DeviceDetilModule::class))
interface DeviceDetilComponent{
    fun inject(activity: KDeviceDetilActivity)
}