package com.kachidoki.me.onenettest.kotlinNEWAPP.model

import com.chinamobile.iot.onenet.OneNetApi
import com.chinamobile.iot.onenet.OneNetError
import com.chinamobile.iot.onenet.OneNetResponse
import com.chinamobile.iot.onenet.ResponseListener
import com.google.gson.Gson
import com.kachidoki.me.onenettest.OLDAPP.config.API
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.*
import javax.inject.Inject

/**
 * Created by Kachidoki on 2017/6/1.
 */
class OneNetModel
@Inject constructor(
        private val onenet:OneNetApi,
        private val gosn:Gson):OneNetLocalApi{
    override fun getDevices(callback: LocalCallBack<DeviceListWrapper>,page: String?, perpage: String?, keyWords: String?, tag: String?, online: String?, isPrivate: String?) {
        onenet.getDevices(API.APIKey,page,perpage,keyWords,tag,online,isPrivate,object : ResponseListener{
            override fun onResponse(p0: OneNetResponse?) {
                val res:DeviceListWrapper=gosn.fromJson(p0!!.data,DeviceListWrapper::class.java)
                callback.OnSucceed(res)
            }

            override fun onError(p0: OneNetError?) {
                callback.OnFail(p0)
            }

        })
    }

    override fun getDevice(deviceId: String, callback: LocalCallBack<DeviceDetilWrapper>) {

    }

    override fun getDatastreams(deviceId: String, datastreamIds: Array<String>?, callback: LocalCallBack<DatastreamsWraper>) {

    }

    override fun getDatastream(deviceId: String, callback: LocalCallBack<DataSingleWraper>, streamId: String?) {

    }

    override fun sendToEdp(deviceId: String, command: String, callback: LocalCallBack<Nothing>) {

    }

    override fun getDataPoints(deviceId: String, datastreamId: String, start: String?, end: String?, limit: String?, cursor: String?, duration: String?, callback: LocalCallBack<DataPointsWrapper>) {

    }

}

