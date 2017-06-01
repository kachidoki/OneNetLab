package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module

import android.content.Context
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceListContract
import dagger.Module
import dagger.Provides

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Module
class DeviceListModule(private val view:DeviceListContract.View){
    @Provides fun getView()=view
}