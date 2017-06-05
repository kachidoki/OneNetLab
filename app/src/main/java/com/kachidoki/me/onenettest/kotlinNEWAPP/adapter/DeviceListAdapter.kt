package com.kachidoki.me.onenettest.kotlinNEWAPP.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kachidoki.me.onenettest.databinding.ItemKdevicelistBinding
import com.kachidoki.me.onenettest.kotlinNEWAPP.activity.KDeviceDetilActivity
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceList

/**
 * Created by Kachidoki on 2017/6/1.
 */
class DeviceListAdapter(private val list:MutableList<DeviceList>):BaseBindingAdapter<ItemKdevicelistBinding>(){

    val safe = mutableMapOf<String,Int>()

    fun setSafe(map:Map<String,Int>){
        safe.clear()
        safe.putAll(map)
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
    }

    fun addAll(res:List<DeviceList>){
        list.addAll(res)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBViewHolder<ItemKdevicelistBinding> {
        return DBViewHolder(
                ItemKdevicelistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: DBViewHolder<ItemKdevicelistBinding>, position: Int) {
        holder.binding.devicelist=list.get(position)
        if (!safe.isEmpty()){
            holder.binding.devicepoint=safe.get(list[position].id)?:0
        }
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            KDeviceDetilActivity.GoKDeviceDetilActivity(list[position].id,holder.itemView.context)
        }
    }

    override fun getItemCount() = list.size

}