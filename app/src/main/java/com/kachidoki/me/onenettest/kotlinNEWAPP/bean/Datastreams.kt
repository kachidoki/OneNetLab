package com.kachidoki.me.onenettest.kotlinNEWAPP.bean

/**
 * Created by Kachidoki on 2017/6/1.
 */
data class Datastreams(
        val create_time:String,
        val update_at:String,
        val current_value:String,
        val id:String,
        val unit:String,
        val unit_symbol:String,
        val uuid:String
)

data class DatastreamsWraper(
        val errno:Int,
        val error:String,
        val data:List<Datastreams>
)

data class DataSingleWraper(
        val errno:Int,
        val error:String,
        val data:Datastreams
)
