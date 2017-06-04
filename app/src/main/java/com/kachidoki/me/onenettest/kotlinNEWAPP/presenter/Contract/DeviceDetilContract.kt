package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract

import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Datastreams
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil

/**
 * Created by Kachidoki on 2017/6/4.
 */
interface DeviceDetilContract{
    interface Presenter{
        fun getStream(deviceId:String,streamId:String?=null)

        fun getDetil(deviceId:String)

        fun sendCommand(deviceId: String,command:String)
    }

    interface View{
        fun showDetil(detil:DeviceDetil)

        fun showIsOpen(isOpen:Boolean)

        fun showStreams(res:List<Datastreams>)

        fun showSafe(safe:String)

        fun showSendOk()

        fun showFail(e:Exception)
    }
}