package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter

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
        model.getDevices(LocalResponse(
                {list -> view.setData(list.devices)},
                {e -> view.failData(e)}
        ))
    }


}