package com.kachidoki.me.onenettest.kotlinNEWAPP.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kachidoki.me.onenettest.R
import com.kachidoki.me.onenettest.databinding.ActivityKdeviceDetilBinding
import com.kachidoki.me.onenettest.kotlinNEWAPP.Di.module.DeviceDetilModule
import com.kachidoki.me.onenettest.kotlinNEWAPP.adapter.DeviceDetilAdapter
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.KBaseActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.getApiComponent
import com.kachidoki.me.onenettest.kotlinNEWAPP.app.toast
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Datastreams
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceDetil
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.Contract.DeviceDetilContract
import com.kachidoki.me.onenettest.kotlinNEWAPP.presenter.DeviceDetilPresenter
import javax.inject.Inject

/**
 * Created by Kachidoki on 2017/6/4.
 */
class KDeviceDetilActivity:KBaseActivity<ActivityKdeviceDetilBinding>(),DeviceDetilContract.View{

    @Inject lateinit var presenter:DeviceDetilPresenter
    private lateinit var adapter:DeviceDetilAdapter
    private lateinit var deviceID:String

    override fun initView() {
        getApiComponent().plus(DeviceDetilModule(this)).inject(this)
        adapter= DeviceDetilAdapter(ArrayList<Datastreams>())
        with(mBinding!!){
            mBinding.KdeviceDetilRecyclerview.adapter=adapter
            mBinding.KdeviceDetilRecyclerview.setLayoutManager(LinearLayoutManager(this@KDeviceDetilActivity))
            mBinding.KdeviceDetilRecyclerview.setRefreshListener {
                presenter.getStream(deviceID)
            }
            presenter.getStream(deviceID)
            presenter.getDetil(deviceID)
        }
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityKdeviceDetilBinding {
        val intent:Intent=this.intent
        deviceID=intent.getStringExtra(ID)
        return DataBindingUtil.setContentView(this, R.layout.activity_kdevice_detil)
    }

    override fun showDetil(detil: DeviceDetil) {
        mBinding.detil=detil
    }

    override fun showIsOpen(isOpen: Boolean) {
        mBinding.isOpen=isOpen
    }

    override fun showStreams(res: List<Datastreams>) {
        adapter.setData(res)
    }

    override fun showSafe(safe: String) {
        mBinding.safe=safe
    }

    override fun showSendOk() {
        toast("发送成功")
    }

    override fun showFail(e: Exception) {
        toast("发送失败")
    }

    companion object{
        val ID:String="ID"
        val SAFE:String="安全指数"
        val SWITCH:String="开关状态"
        val OPEN:String="开"

        fun GoKDeviceDetilActivity(id:String,context: Context){
            var intent=Intent(context,KDeviceDetilActivity::class.java)
            intent.putExtra(ID,id)
            context.startActivity(intent)
        }
    }


}