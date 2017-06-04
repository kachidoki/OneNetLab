package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter

import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceDetilActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Datastreams
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalCallBack
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
        model.getDevice(deviceId,object :LocalCallBack<DeviceDetil>{
            override fun OnSucceed(t: DeviceDetil) {
                view.showDetil(t)
            }

            override fun OnFail(e: Exception?) {
                view.showFail(e!!)
            }
        })
    }

    override fun sendCommand(deviceId: String, command: String) {
        model.sendToEdp(deviceId,command,object :LocalCallBack<Unit>{
            override fun OnSucceed(t: Unit) {
                view.showSendOk()
            }

            override fun OnFail(e: Exception?) {
                view.showFail(e!!)
            }
        })
    }

    override fun getStream(deviceId: String, streamId: String?) {
        when(streamId){
            null->{
                model.getDatastreams(object :LocalCallBack<List<Datastreams>>{
                    override fun OnSucceed(t: List<Datastreams>) {
                        view.showStreams(
                            t.filter {
                                if (it.id.equals(KDeviceDetilActivity.SAFE)) view.showSafe(it.current_value)
                                else if (it.id.equals(KDeviceDetilActivity.SWITCH)) view.showIsOpen(it.current_value.equals(KDeviceDetilActivity.OPEN))
                                !(it.id.equals(KDeviceDetilActivity.SAFE)||it.id.equals(KDeviceDetilActivity.SWITCH))
                            }
                        )
                    }
                    override fun OnFail(e: Exception?) {
                        view.showFail(e!!)
                    }
                },deviceId)
            }
            KDeviceDetilActivity.SAFE->{
                model.getDatastream(deviceId,object :LocalCallBack<Datastreams>{
                    override fun OnSucceed(t: Datastreams) {
                        view.showSafe(t.current_value)
                    }
                    override fun OnFail(e: Exception?) {
                        view.showFail(e!!)
                    }
                },streamId)
            }
            KDeviceDetilActivity.SWITCH->{
                model.getDatastream(deviceId,object :LocalCallBack<Datastreams>{
                    override fun OnSucceed(t: Datastreams) {
                        view.showIsOpen(t.current_value.equals(KDeviceDetilActivity.OPEN))
                    }
                    override fun OnFail(e: Exception?) {
                        view.showFail(e!!)
                    }
                },streamId)
            }
            else -> Unit
        }
    }


}