package com.kachidoki.me.onenettest

import com.chinamobile.iot.onenet.OneNetApi
import com.chinamobile.iot.onenet.OneNetResponse
import com.chinamobile.iot.onenet.ResponseListener
import com.google.gson.Gson
import com.kachidoki.me.onenettest.OLDAPP.config.API
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Binary
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Key
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Location
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalResponse
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.Mockito.*
import org.junit.Assert.*;
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Matchers
import org.mockito.Matchers.*

/**
 * Created by Kachidoki on 2017/6/9.
 */
@RunWith(MockitoJUnitRunner::class)
class KModelApiTest{

    val gson:Gson = Gson()

    @Mock
    lateinit var onenet:OneNetApi

    @Mock
    lateinit var detilCall:LocalResponse<DeviceDetil>

    @Captor
    lateinit var onenetCall:ArgumentCaptor<ResponseListener>

    lateinit var model:OneNetModel

    val deviceId:String = "123123"

    val detil:DeviceDetil = DeviceDetil("123",false,"123","345","123124","asdasf", Binary("123","sds",1,"dsf"), emptyArray(), Location(123.2F,123.3F,345.4F),"asgtrvb"
    ,"asfvbr", Key("asdasf","dgs"), emptyList())

    @Before
    fun setUp(){
        model=OneNetModel(onenet,gson)
    }

    @Test
    fun onenetIsDo(){
        model.getDevice(deviceId,detilCall)
        verify(onenet).getDevice(eq(API.APIKey),eq(deviceId), any(ResponseListener::class.java))
    }

    @Test
    fun DetilTest(){
        model.getDevice(deviceId,detilCall)
        verify(onenet).getDevice(eq(API.APIKey), eq(deviceId),onenetCall.capture())
        onenetCall.value.onResponse(Matchers.any(OneNetResponse::class.java))
        verify(detilCall).s.invoke(Matchers.any(DeviceDetil::class.java))
    }
}