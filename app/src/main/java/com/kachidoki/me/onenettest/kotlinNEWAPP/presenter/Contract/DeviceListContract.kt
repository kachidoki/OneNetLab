package com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract

import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceList

/**
 * Created by Kachidoki on 2017/6/1.
 */
interface DeviceListContract{
    interface View{
        fun setData(res:List<DeviceList>)
        fun failData(e:Exception)
        fun showsafe(map:Map<String,Int>)
    }

    interface Presenter{
        fun getData(page:Int?=0)
    }
}