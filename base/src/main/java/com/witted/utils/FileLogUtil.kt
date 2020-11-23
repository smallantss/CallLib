package com.witted.utils

import android.os.Environment
import com.blankj.utilcode.util.FileIOUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*

object FileLogUtil {

    //是否写日志
    var write: Boolean = true

    private val callLogFile =
        File(Environment.getExternalStorageDirectory().absolutePath + File.separator + LOG_NAME)

    private val callConnectFile =
        File(Environment.getExternalStorageDirectory().absolutePath + File.separator + CALL_CONNECT_NAME)

    val deviceLogFile =
        File(Environment.getExternalStorageDirectory().absolutePath + File.separator + DEVICE_LOG_NAME)

    val exceptionFile =
        File(Environment.getExternalStorageDirectory().absolutePath + File.separator + EXCEPTION_NAME)

    private val callsFile =
        File(Environment.getExternalStorageDirectory().absolutePath + File.separator + CALLS_NAME)

    //写文件
    fun write(msg: String, f: File = callLogFile) {
        try {
            if (write.not()) {
                return
            }
            val dispose = Observable.just(DateUtils.simpleYMDHMS(Date()).plus(" $msg").plus("\n"))
                .subscribeOn(Schedulers.io())
                .subscribe {
                    FileIOUtils.writeFileFromString(f, it, true)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun writeConnect(msg: String) {
        write(msg, callConnectFile)
    }

    fun writeDeviceLog(msg: String) {
        write(msg, deviceLogFile)
    }

    fun writeException(msg: String) {
        write(msg, exceptionFile)
    }

    fun writeCalls(msg: String){
        write(msg, callsFile)
    }

}