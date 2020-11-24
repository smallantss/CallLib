package com.witted.base

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.witted.ext.hideNavigation
import com.witted.ext.loge
import com.witted.ext.openWifi
import com.witted.ext.toast
import com.witted.receiver.NetReceiver
import com.witted.utils.*
import com.witted.widget.LoadingDialog
import org.greenrobot.eventbus.EventBus

/**
 * 所有基类的顶级父类
 */
abstract class BaseRootActivity : AppCompatActivity(),
    NetReceiver.NetworkCallback {

    private lateinit var etHelper: EditTextHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        etHelper = EditTextHelper(this)
        NetReceiver.getInstance().register(this, this)
        hideNavigation()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        beforeSetContentView()
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    //设置editText
    open fun setEt(et: EditText) {
        etHelper.et = et
    }

    open fun setEtList(list: List<EditText>) {
        etHelper.etList = list.toMutableList()
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }

    fun beforeSetContentView() {

    }

    abstract fun initView()

    //隐藏虚拟导航栏
    private fun hideNavigation() {
        window.decorView.hideNavigation()
    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    override fun onBackPressed() {
    }

    /**
     * 是否要使用EventBus
     */
    fun useEventBus(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun startActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    open fun startActivityWithData(clazz: Class<*>, bundle: Bundle) {
        startActivity(Intent(this, clazz).apply {
            putExtras(bundle)
        })
    }

    private var loadingDialog: LoadingDialog? = null

    open fun loading(loading: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog().apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_full_screen)
            }
        }
        if (loading) {
            if (loadingDialog?.isAdded == true) {
                return
            }
            loadingDialog?.show(supportFragmentManager, "loading")
        } else {
            dismissLoading()
        }
    }

    fun dismissLoading() {
        loadingDialog?.let {
            if (it.dialog?.isShowing == true) {
                it.dismiss()
            }
        }
        loadingDialog = null
    }

    //呼叫中，此时等待对方接通
//    open fun onCallComing(call: Call?, state: Call.State?) {
//        loge("有人呼叫")
//        CountDownHelper.getInstance().withoutOperate()
//    }
//
//    //对方已接通，开始进行通话
//    open fun onCallCommunication(call: Call?, state: Call.State?) {
//        loge("有人接听")
//    }
//
//    //通话结束
//    open fun onCallEnd(call: Call?, state: Call.State?) {
//        loge("通话结束")
//    }

    override fun onNetChanged(hasNet: Boolean) {
        if (hasNet) {
            loge("网络已连接")
            FileLogUtil.writeConnect("网络已连接")
        } else {
            toast("网络已断开")
            loge("网络已断开")
            FileLogUtil.writeConnect("网络已断开")
            openWifi(this)
        }
    }

    //是否要停止计时
    var blockCountDown = false
        set(value) {
            if (value) {
                releaseTimeDisposable()
            }
            field = value
        }

    //检测事件
    private var detectTime: Int? = null

    //开始计时,每秒计时一次
    fun startCountDown(total: Int = TIME_COUNT) {
        detectTime = total
        if (blockCountDown) {
            return
        }
        CountDownHelper.getInstance().startCountDown(total)
    }

    //释放
    fun releaseTimeDisposable() {
        CountDownHelper.getInstance().releaseTimeDisposable()
    }

    //抬起时开始计时，其他停止计时
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.actionMasked) {
            MotionEvent.ACTION_UP -> {
                if (detectTime != null) {
                    startCountDown(detectTime!!)
                }
            }
            else -> {
                releaseTimeDisposable()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}