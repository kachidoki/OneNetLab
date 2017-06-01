package com.kachidoki.me.onenettest.kotlinNEWAPP.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kachidoki.me.onenettest.databinding.ItemKdevicelistBinding
import com.kachidoki.me.onenettest.kotlinNEWAPP.bean.DeviceList

/**
 * Created by Kachidoki on 2017/6/1.
 */
class DeviceListAdapter(private val list:MutableList<DeviceList>):BaseBindingAdapter<ItemKdevicelistBinding>(){

    fun clear(){
        list.clear()
    }

    fun addAll(nlist:List<DeviceList>){
        list.addAll(nlist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBViewHolder<ItemKdevicelistBinding> {
        return DBViewHolder(
                ItemKdevicelistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: DBViewHolder<ItemKdevicelistBinding>, position: Int) {
        super.bindViewHolder(holder,position)
        holder.binding.devicelist=list.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

}