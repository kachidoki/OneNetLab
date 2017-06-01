package com.kachidoki.me.onenettest.kotlinNEWAPP.bean

/**
 * Created by Kachidoki on 2017/6/1.
 */
data class DeviceDetil(
        val id:String,
        val online:Boolean,
        val protocol:String,
        val title:String,
        val desc:String,
        val create_time:String,
        val binary:Binary,
        val tags:Array<String>,
        val location:Location,
        val auth_info:String,
        val other:String,
        val keys:Key,
        val datastreams:List<Datastreams>
)

data class Key(
        val title:String,
        val key:String
)

data class Binary(
        val index:String,
        val at:String,
        val size:Int,
        val desc:String
)

data class Location(
        val lon:Float,
        val lat:Float,
        val ele:Float?
)

data class DeviceDetilWrapper(
        val errno:Int,
        val error:String,
        val data:DeviceDetil
)