package com.witted.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.witted.ext.hideInput
import com.witted.ext.toast
import java.lang.reflect.ParameterizedType
import kotlin.properties.Delegates

/**
 * Mvvm模式Activity的基类
 */
abstract class BaseMvvmActivity<T : ViewDataBinding, VM : BaseViewModel>(val layoutId: Int) :
    BaseRootActivity() {

    private var binding: T? = null
    private var viewModelId = BR.viewModel
    var viewModel: VM by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        initUIChange()
        initView()
        initData(savedInstanceState)
    }

    override fun initView() {

    }

    //初始化DataBinding
    private fun initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModelId = initViewModelId()
        val vm = initViewMode()
        if (vm == null) {
            val modelClass: Class<BaseViewModel>?
            val type = javaClass.genericSuperclass
            modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<BaseViewModel>
            } else {
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        } else {
            viewModel = vm
        }
        binding?.setVariable(viewModelId, viewModel)
        binding?.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
    }

    /**
     * 获取ViewModel的实例
     */
    private fun createViewModel(
        fragmentActivity: FragmentActivity,
        clazz: Class<BaseViewModel>
    ): BaseViewModel {
        return ViewModelProvider(fragmentActivity).get(clazz)
    }

    /**
     * 注册UI的更改
     */
    private fun initUIChange() {
        viewModel.apply {
            startActivityEvent.observe(this@BaseMvvmActivity, Observer {
                startActivity(it)
            })
            startActivityPair.observe(this@BaseMvvmActivity, Observer {
                startActivityWithData(it.first, it.second)
            })
            loadingEvent.observe(this@BaseMvvmActivity, Observer {
                loading(it)
            })
            toastEvent.observe(this@BaseMvvmActivity, Observer {
                toast(it)
            })
            finishEvent.observe(this@BaseMvvmActivity, Observer {
                finish()
            })
            hideInput.observe(this@BaseMvvmActivity, Observer {
                this@BaseMvvmActivity.window.decorView.hideInput()
            })
        }
    }

    /**
     * 初始化ViewModelId
     * 可通过重写重新设置
     */
    fun initViewModelId(): Int {
        return BR.viewModel
    }

    /**
     * 获取ViewModel的实例
     * 可通过重写获取
     */
    fun initViewMode(): VM? {
        return null
    }

//    //全局共享的ViewModel
//    fun sharedViewModel() = DoorApp.app.getSharedViewModel()

    override fun onNetChanged(hasNet: Boolean) {
        super.onNetChanged(hasNet)
//        sharedViewModel().netConnect.value = true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
        binding = null
    }
}