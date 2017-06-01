package com.kachidoki.me.onenettest.kotlinNEWAPP.bean

/**
 * Created by Kachidoki on 2017/6/1.
 */
data class DataPoints(
        val at:String,
        val value:String
)

data class DataPointStreams(
        val datapoints:List<DataPoints>,
        val id:String
)

data class DataPointsWrapper(
        val count:Int,
        val datastreams:List<DataPointStreams>
)
