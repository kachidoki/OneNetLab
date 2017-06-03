package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module

import android.content.Context
import com.chinamobile.iot.onenet.OneNetApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kachidoki.me.onenettest.kotlinNEWAPP.model.OneNetModel
import dagger.Module
import dagger.Provides

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule{

    @Provides fun provideGson() = Gson()

    @Provides fun provideOneNet(context:Context) = OneNetApi.getInstance(context)

    @Provides fun provideOneNetModel(onenet:OneNetApi,gson:Gson) = OneNetModel(onenet,gson)
}