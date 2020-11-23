package com.witted.base

import androidx.lifecycle.MutableLiveData

/**
 * 全局共享的ViewModel
 */
class SharedViewModel: BaseViewModel() {

    //设置IP地址
    val ip = MutableLiveData<Int>()

    //更新呼叫配置的数据，如devId和呼叫ID
    val data = MutableLiveData<String>()

    //呼叫的对象
//    val callBean = MutableLiveData<CallBean>()

    //是否展示首页
    val showHome = MutableLiveData<Boolean>()

    val netConnect = MutableLiveData<Boolean>()

}