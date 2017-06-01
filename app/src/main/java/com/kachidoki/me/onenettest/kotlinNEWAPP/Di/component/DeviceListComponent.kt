package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.component

import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceListModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceListActivity
import dagger.Subcomponent

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Subcomponent(modules = arrayOf(DeviceListModule::class))
interface DeviceListComponent{
    fun inject(activity:KDeviceListActivity)
}