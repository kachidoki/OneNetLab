package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter

import android.util.Log
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceListWrapper
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalCallBack
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetLocalApi
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceListContract
import javax.inject.Inject

/**
 * Created by Kachidoki on 2017/6/1.
 */
class DeviceListPresent
 @Inject constructor(private val model:OneNetModel
                         , private val view:DeviceListContract.View)
    :DeviceListContract.Presenter {


    override fun getData(page: Int?) {
        model.getDevices(object :LocalCallBack<DeviceListWrapper>{
            override fun OnSucceed(t: DeviceListWrapper) {
                view.setData(t.devices)
            }

            override fun OnFail(e: Exception?) {
                view.failData(e!!)
            }

        })
    }


}