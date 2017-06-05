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


    override fun getDevices(callback: LocalResponse<DeviceListWrapper>, page: String?, perpage: String?, keyWords: String?, tag: String?, online: String?, isPrivate: String?) {
        onenet.getDevices(API.APIKey,page,perpage,keyWords,tag,online,isPrivate,object : ResponseListener{
            override fun onResponse(p0: OneNetResponse) {
                val res:DeviceListWrapper=gosn.fromJson(p0.data,DeviceListWrapper::class.java)
                callback.s(res)
            }

            override fun onError(p0: OneNetError) {
                callback.e(p0)
            }

        })
    }

    override fun getDevice(deviceId: String, callback: LocalResponse<DeviceDetil>) {
        onenet.getDevice(API.APIKey,deviceId,object :ResponseListener{
            override fun onResponse(p0: OneNetResponse) {
                val res:DeviceDetilWrapper=gosn.fromJson(p0.rawResponse,DeviceDetilWrapper::class.java)
                callback.s(res.data)
            }

            override fun onError(p0: OneNetError) {
                callback.e(p0)
            }
        })
    }


    override fun getDatastreams(callback: LocalResponse<List<Datastreams>>, deviceId: String, datastreamIds: Array<String>?) {
        onenet.getDatastreams(API.APIKey,deviceId,datastreamIds,object :ResponseListener{
            override fun onResponse(p0: OneNetResponse) {
                val res:DatastreamsWraper=gosn.fromJson(p0.rawResponse,DatastreamsWraper::class.java)
                callback.s(res.data)
            }

            override fun onError(p0: OneNetError) {
                callback.e(p0)
            }
        })
    }


    override fun getDatastream(deviceId: String, streamId: String, callback: LocalResponse<Datastreams>) {
        onenet.getDatastream(API.APIKey,deviceId,streamId,object :ResponseListener{
            override fun onResponse(p0: OneNetResponse) {
                val res:DataSingleWraper=gosn.fromJson(p0.data,DataSingleWraper::class.java)
                callback.s(res.data)
            }

            override fun onError(p0: OneNetError) {
                callback.e(p0)
            }
        })
    }

    override fun sendToEdp(deviceId: String, command: String, callback: LocalResponse<Unit>) {
        onenet.sendToEdp(API.APIKey,deviceId,command,object :ResponseListener{
            override fun onResponse(p0: OneNetResponse) {
                callback.s(Unit)
            }

            override fun onError(p0: OneNetError) {
                callback.e(p0)
            }
        })
    }

    override fun getDataPoints(deviceId: String, datastreamId: String, start: String?, end: String?, limit: String?, cursor: String?, duration: String?, callback: LocalResponse<DataPointsWrapper>) {

    }

}

