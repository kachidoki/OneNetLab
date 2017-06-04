package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module

import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceDetilContract
import dagger.Module
import dagger.Provides

/**
 * Created by Kachidoki on 2017/6/4.
 */
@Module
class DeviceDetilModule(private val view:DeviceDetilContract.View){
    @Provides fun getView() = view
}