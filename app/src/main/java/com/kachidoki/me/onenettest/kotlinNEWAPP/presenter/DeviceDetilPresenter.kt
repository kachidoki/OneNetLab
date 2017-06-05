package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter

import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceDetilActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalResponse
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceDetilContract
import javax.inject.Inject

/**
 * Created by Kachidoki on 2017/6/4.
 */
class DeviceDetilPresenter
@Inject constructor(private val model: OneNetModel
                    , private val view:DeviceDetilContract.View)
    :DeviceDetilContract.Presenter {

    override fun getDetil(deviceId: String) {
        model.getDevice(deviceId, LocalResponse(
                {t -> view.showDetil(t) },
                {exception -> view.showFail(exception) }
        ))

    }

    override fun sendCommand(deviceId: String, command: String) {
        model.sendToEdp(deviceId,command, LocalResponse(
                {view.showSendOk()},
                {exception ->  view.showFail(exception)}
        ))
    }

    override fun getStream(deviceId: String, streamId: String?) {
        when(streamId){
            null->{
                model.getDatastreams(LocalResponse(
                        { list -> view.showStreams(
                                list.filter {
                                    if (it.id.equals(KDeviceDetilActivity.SAFE)) view.showSafe(it.current_value)
                                    else if (it.id.equals(KDeviceDetilActivity.SWITCH)) view.showIsOpen(it.current_value.equals(KDeviceDetilActivity.OPEN))
                                    !(it.id.equals(KDeviceDetilActivity.SAFE)||it.id.equals(KDeviceDetilActivity.SWITCH))
                                }
                        )},
                        {exception -> view.showFail(exception) }
                ),deviceId)
            }
            KDeviceDetilActivity.SAFE->{
                model.getDatastream(deviceId,streamId, LocalResponse(
                        {d -> view.showSafe(d.current_value) },
                        {exception -> view.showFail(exception) }
                ))
            }
            KDeviceDetilActivity.SWITCH->{
                model.getDatastream(deviceId,streamId, LocalResponse(
                        {t -> view.showIsOpen(t.current_value.equals(KDeviceDetilActivity.OPEN)) },
                        {exception -> view.showFail(exception) }
                ))
            }
            else -> Unit
        }
    }


}