package com.witted.utils

import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.witted.base.BaseRootActivity

/**
 * EditText的辅助类
 */
class EditTextHelper(private val owner: BaseRootActivity) : LifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    var et: EditText? = null
        set(value) {
            field = value
            value?.addTextChangedListener(watcher)
        }

    var etList: MutableList<EditText>? = null
        set(value) {
            field = value
            value?.forEach {
                it.addTextChangedListener(watcher)
            }
        }

    private val watcher = TextInputWatcher({
        //变化的时候
        owner.releaseTimeDisposable()
    }, {
        //停止输入
        owner.startCountDown()
    })

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        owner.releaseTimeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        owner.startCountDown()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        et?.removeTextChangedListener(watcher)
        etList?.forEach {
            it.removeTextChangedListener(watcher)
        }
        owner.lifecycle.removeObserver(this)
    }

}