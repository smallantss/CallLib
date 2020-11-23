package com.witted.utils

import android.content.Intent
import com.witted.base.ext.logd
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class CountDownHelper private constructor() {

    companion object {
        @Volatile
        private var instance: CountDownHelper? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CountDownHelper().also {
                    instance = it
                }
            }
    }

    //停止计数，如果未设置完整参数，需要全部停止计数
    var blockCount = false

    //无操作,跳转到首页
    fun withoutOperate() {
        logd("停止计时:$blockCount")
        if (blockCount) {
            return
        }
//        ActivityManager.currentActivity()?.let {
//            if (it !is MainActivity) {
//                it.startActivity(Intent(it, MainActivity::class.java))
//            }
//        }
    }

    //检测事件
    private var detectTime: Int? = null

    private var timeObservable: Disposable? = null

    //开始计时,每秒计时一次
    fun startCountDown(total: Int) {
        releaseTimeDisposable()
        detectTime = total
        logd("开始计时:$total")
        timeObservable = Observable.intervalRange(0, total.toLong(), 0, 1, TimeUnit.SECONDS)
            .map {
                total - it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
            }
            .doOnComplete {
                withoutOperate()
            }
            .subscribe()
    }

    //释放
    fun releaseTimeDisposable() {
        logd("取消计时")
        if (timeObservable?.isDisposed == false) {
            timeObservable?.dispose()
        }
    }
}