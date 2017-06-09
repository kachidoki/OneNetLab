package com.kachidoki.me.onenettest.kotlinNEWAPP.model

import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.*


/**
 * Created by Kachidoki on 2017/6/1.
 */
interface OneNetLocalApi{
    fun getDevices(
            callback:LocalResponse<DeviceListWrapper>,
            page:String?=null,
            perpage:String?=null,
            keyWords:String?=null,
            tag:String?=null,
            online:String?=null,
            isPrivate:String?=null
    )

    fun getDevice(
            deviceId:String,
            callback:LocalResponse<DeviceDetil>
    )

    fun getDatastreams(
            callback:LocalResponse<List<Datastreams>>,
            deviceId:String,
            datastreamIds:Array<String>?=null
    )


    fun getDatastream(
            deviceId:String,
            streamId:String,
            callback:LocalResponse<Datastreams>
    )

    fun sendToEdp(
            deviceId:String,
            command:String,
            callback: LocalResponse<Unit>
    )


    fun getDataPoints(
            deviceId:String,
            datastreamId:String,
            start:String?=null,
            end:String?=null,
            limit:String?=null,
            cursor:String?=null,
            duration:String?=null,
            callback:LocalResponse<DataPointsWrapper>
    )


}

open class LocalResponse<T>(val s:(T)->Unit, val e:(Exception)->Unit)