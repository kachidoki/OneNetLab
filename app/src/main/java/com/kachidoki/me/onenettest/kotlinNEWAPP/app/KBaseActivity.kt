package com.kachidoki.me.onenettest.kotlinNEWAPP.app

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Kachidoki on 2017/6/1.
 */
abstract class KBaseActivity<B :ViewDataBinding> :AppCompatActivity(){

    lateinit var mBinding:B

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mBinding=createDataBinding(savedInstanceState)
        initView()
    }

    abstract fun initView()


    abstract fun  createDataBinding(savedInstanceState: Bundle?): B


}
