package com.witted.utils

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.witted.base.BaseRootActivity

/**
 * EditText的辅助类
 */
class EditTextHelper(private val owner: BaseRootActivity) : LifecycleObserver {

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

    private val mEtList = ArrayList<EditText>()

    init {
        owner.lifecycle.addObserver(this)
//        val root = (owner.window.decorView as FrameLayout)
//        addEt(root)
//        etList = mEtList
    }

    private fun addEt(root: View) {
        if (root is ViewGroup) {
            val iterator = root.children.iterator()
            while (iterator.hasNext()) {
                val i = iterator.next()
                addEt(i)
            }
        } else if (root is EditText) {
            mEtList.add(root)
        }
    }


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