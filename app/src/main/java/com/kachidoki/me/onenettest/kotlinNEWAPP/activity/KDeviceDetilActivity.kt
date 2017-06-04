package com.kachidoki.me.onenettest.kotlinNEWAPP.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.afollestad.materialdialogs.MaterialDialog
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
import kotlinx.android.synthetic.main.pop_list.view.*
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
            mBinding.view=this@KDeviceDetilActivity
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

    override fun showPop(v:View,context: Context){
        val content:View=LayoutInflater.from(context).inflate(R.layout.pop_list,null)
        content.pop_wifi.setOnClickListener { showSetWifi(context) }
        content.pop_clock.setOnClickListener { showSetColck(context) }
        val pop:PopupWindow= PopupWindow(content,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true)
        pop.showAsDropDown(v)
    }

    fun showSetWifi(context: Context){
        MaterialDialog.Builder(context)
                .title("设置wifi")
                .content("输入wifi名")
                .inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2,12)
                .positiveText("确定")
                .onPositive { dialog, which -> dialog.dismiss() }
                .show()
    }

    fun showSetColck(context: Context){

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