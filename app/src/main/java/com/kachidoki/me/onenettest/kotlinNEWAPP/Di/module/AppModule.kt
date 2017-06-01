package com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Kachidoki on 2017/6/1.
 */
@Module
class AppModule(private val context:Context){
    @Provides fun provideContext()=context
}