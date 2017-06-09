package com.kachidoki.me.onenettest

import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Binary
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Key
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Location
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalResponse
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceDetilContract
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.DeviceDetilPresenter
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Kachidoki on 2017/6/9.
 */
class KDetilPresenterTest{

    @Mock
    lateinit var view:DeviceDetilContract.View

    @Mock
    lateinit var model:OneNetModel

    @Captor
    val callCaptor:ArgumentCaptor<LocalResponse<DeviceDetil>>? = null

    lateinit private var presenter:DeviceDetilContract.Presenter

    val detil:DeviceDetil = DeviceDetil("123",false,"123","345","123124","asdasf",
            Binary("123","sds",1,"dsf"), emptyArray(),
            Location(123.2F,123.3F,345.4F),"asgtrvb","asfvbr"
            , Key("asdasf","dgs"), emptyList())

    val deviceId:String = "123123"

    @Before
    fun SetUp(){
        MockitoAnnotations.initMocks(this)
        presenter=DeviceDetilPresenter(model,view)
    }

    @Test
    fun TestGetDetil(){
        presenter.getDetil(deviceId)
        assertNotNull(callCaptor)
        verify<OneNetModel>(model).getDevice(deviceId,callCaptor!!.capture())
    }
}