package com.kachidoki.me.onenettest.kotlinNEWAPP.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.afollestad.materialdialogs.MaterialDialog
import com.kachidoki.me.onenettest.OLDAPP.config.API
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
import kotlinx.android.synthetic.main.dialog_wifi.view.*
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
        val v:View=LayoutInflater.from(context).inflate(R.layout.dialog_wifi,null)
        val dialog:AlertDialog=AlertDialog.Builder(context)
                .setTitle("设置Wifi")
                .setView(v)
                .setNegativeButton("取消",null)
                .setPositiveButton("确定",null)
                .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!TextUtils.isEmpty(v.wifi_name.editText!!.text.toString())&&!TextUtils.isEmpty(v.wifi_psw.editText!!.text.toString())){
                presenter.sendCommand(deviceID,API.COMMAND_WIFI(v.wifi_name.editText!!.text.toString(),v.wifi_psw.editText!!.text.toString()))
                dialog.dismiss()
            }else{
                if (TextUtils.isEmpty(v.wifi_name.editText!!.text.toString())) v.wifi_name.error="请输入wifi名"
                if (TextUtils.isEmpty(v.wifi_psw.editText!!.text.toString())) v.wifi_psw.error="请输入wifi名"
            }
        }

    }

    fun showSetColck(context: Context){
        MaterialDialog.Builder(context)
                .title("设置定时关闭")
                .content("请输入时间/分钟")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("分钟",null,false,{dialog, input ->
                    val time=input.toString().toInt()
                    presenter.sendCommand(deviceID,API.COMMAND_TIME(time))
                })
                .positiveText("确定")
                .negativeText("取消")
                .onNegative { dialog, which -> toast("negative")}
                .neutralText("取消定时")
                .onNeutral { dialog, which -> presenter.sendCommand(deviceID,API.COMMAND_CANCELTIME) }
                .show()
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