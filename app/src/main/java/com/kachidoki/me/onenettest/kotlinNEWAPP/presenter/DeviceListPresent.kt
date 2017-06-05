package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter

import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceDetilActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalResponse
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
        val safe= mutableMapOf<String,Int>()
        model.getDevices(LocalResponse(
                {
                    wrapper -> view.setData(wrapper.devices)
                                wrapper.devices.forEach {
                                    it-> model.getDatastream(it.id,KDeviceDetilActivity.SAFE, LocalResponse(
                                        {
                                            t -> safe.put(it.id,t.current_value.toInt())
                                                if (safe.size==wrapper.devices.size) view.showsafe(safe)
                                        },
                                        {exception -> view.failData(exception) }
                                    ))
                                }

                },
                {e -> view.failData(e)}
        ))
    }


}