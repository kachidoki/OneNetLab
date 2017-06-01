package com.kachidoki.me.onenettest.kotlinNEWAPP.bean

/**
 * Created by Kachidoki on 2017/5/31.
 */
data class DeviceList(
        val id:String,
        val title:String,
        val desc:String,
        val protocol:String,
        val route_to:String,
        val online:Boolean,
        val tags:Array<String>,
        val create_time:String,
        val location:Location,
        val auth_info:String
)


data class DeviceListWrapper(
        val total_count:Int,
        val per_page:Int,
        val page:Int,
        val devices:List<DeviceList>
){
    fun hasdevices():Boolean{
        return !devices.isEmpty()
    }
}