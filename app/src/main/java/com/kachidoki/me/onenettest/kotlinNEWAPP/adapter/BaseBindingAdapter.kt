package com.kachidoki.me.onenettest.kotlinNEWAPP.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by Kachidoki on 2017/6/1.
 */
abstract class BaseBindingAdapter<B:ViewDataBinding>:RecyclerView.Adapter<DBViewHolder<B>>()