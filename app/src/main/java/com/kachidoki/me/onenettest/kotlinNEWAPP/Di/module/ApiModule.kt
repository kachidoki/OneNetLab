package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module

import android.content.Context
import com.chinamobile.iot.onenet.OneNetApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule{

    @Provides fun provideGson() = GsonBuilder().create()

    @Provides fun provideOneNet(context:Context) = OneNetApi.getInstance(context)
}