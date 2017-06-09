package com.kachidoki.me.onenettest;

import com.chinamobile.iot.onenet.OneNetApi;
import com.chinamobile.iot.onenet.ResponseListener;
import com.google.gson.Gson;
import com.kachidoki.me.onenettest.OLDAPP.config.API;
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil;
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.LocalResponse;
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by Kachidoki on 2017/6/9.
 */
@RunWith(MockitoJUnitRunner.class)
public class ModelApiTest {

    private Gson gson;

    @Mock
    private OneNetApi oneNet;

    @Mock
    private LocalResponse<DeviceDetil> detilCall;

    private OneNetModel model;
    private final String deviceId="123123";

    @Before
    public void setUp(){
        gson = new Gson();
        model = new OneNetModel(oneNet,gson);
    }

    @Test
    public void testNetIsDo(){
        model.getDevice(deviceId,detilCall);
        verify(oneNet).getDevice(eq(API.APIKey),eq(deviceId),any(ResponseListener.class));
    }



}
