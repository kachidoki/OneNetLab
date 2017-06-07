package com.kachidoki.me.onenettest.kotlinNEWAPP.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kachidoki.me.onenettest.OLDAPP.main.DatastreamChartActivity
import com.kachidoki.me.onenettest.databinding.ItemKdevicestreamBinding
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.Datastreams

/**
 * Created by Kachidoki on 2017/6/4.
 */
class DeviceDetilAdapter(private val list:MutableList<Datastreams>,private val deviceId:String):BaseBindingAdapter<ItemKdevicestreamBinding>(){

    fun setData(res: List<Datastreams>){
        list.clear()
        list.addAll(res)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBViewHolder<ItemKdevicestreamBinding> {
        return DBViewHolder(
                ItemKdevicestreamBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }



    override fun onBindViewHolder(holder: DBViewHolder<ItemKdevicestreamBinding>, position: Int) {
        holder.binding.datastream=list[position]
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            if (!list[position].id.equals("运行状态"))
                DatastreamChartActivity.goToChart(holder.itemView.context,deviceId,list[position].id)
        }
    }


    override fun getItemCount() = list.size

}
