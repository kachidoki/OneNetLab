package com.kachidoki.me.onenettest.kotlinNEWAPP.model

import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.*


/**
 * Created by Kachidoki on 2017/6/1.
 */
interface OneNetLocalApi{
    fun getDevices(
            callback:LocalCallBack<DeviceListWrapper>,
            page:String?=null,
            perpage:String?=null,
            keyWords:String?=null,
            tag:String?=null,
            online:String?=null,
            isPrivate:String?=null
    )

    fun getDevice(
            deviceId:String,
            callback:LocalCallBack<DeviceDetil>
    )

    fun getDatastreams(
            callback:LocalCallBack<List<Datastreams>>,
            deviceId:String,
            datastreamIds:Array<String>?=null
    )


    fun getDatastream(
            deviceId:String,
            callback:LocalCallBack<Datastreams>,
            streamId:String
    )

    fun sendToEdp(
            deviceId:String,
            command:String,
            callback: LocalCallBack<Unit>
    )


    fun getDataPoints(
            deviceId:String,
            datastreamId:String,
            start:String?=null,
            end:String?=null,
            limit:String?=null,
            cursor:String?=null,
            duration:String?=null,
            callback:LocalCallBack<DataPointsWrapper>
    )


}


interface LocalCallBack<T>{
    fun OnSucceed(t:T)
    fun OnFail(e:Exception?)
}