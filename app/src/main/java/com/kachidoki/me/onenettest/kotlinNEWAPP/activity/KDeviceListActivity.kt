package com.kachidoki.me.onenettest.kotlinNEWAPP.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.kachidoki.me.onenettest.R
import com.kachidoki.me.onenettest.databinding.ActivityKdeviceListBinding
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceListModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.adapter.DeviceListAdapter
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.KBaseActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.getApiComponent
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.toast
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceList
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceListContract
import javax.inject.Inject

class KDeviceListActivity : KBaseActivity<ActivityKdeviceListBinding>() ,DeviceListContract.View{

    @Inject lateinit var presenter:DeviceListContract.Presenter
    private lateinit var adapter:DeviceListAdapter

    override fun initView() {
        getApiComponent().plus(DeviceListModule(this)).inject(this)
        adapter= DeviceListAdapter(ArrayList<DeviceList>())
        with(mBinding!!){
            mBinding.KdevicelistRecyclerview.adapter=adapter
            mBinding.KdevicelistRecyclerview.setLayoutManager(LinearLayoutManager(this@KDeviceListActivity))
            mBinding.KdevicelistRecyclerview.setRefreshListener {
                presenter.getData()
            }
        }
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityKdeviceListBinding {
        return DataBindingUtil.setContentView(this,R.layout.activity_kdevice_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kdevice_list)
    }

    override fun setData(res: List<DeviceList>) {
        adapter.clear()
        adapter.addAll(res)
    }

    override fun failData(e: Exception) {
        toast(e.toString())
    }

}
