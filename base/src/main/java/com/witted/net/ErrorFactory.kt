package com.witted.net

import com.witted.bean.BaseResponse
import com.witted.bean.ErrorBean
import com.witted.utils.CODE_ERROR_CONNECT
import com.witted.utils.CODE_ERROR_DOWNLOAD
import com.witted.utils.CODE_ERROR_OTHER
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * 获取ErrorBean对象的工厂类
 */
class ErrorFactory {

    companion object {

        //网络异常
        fun getErrorBean(e: Throwable): ErrorBean {
            return when (e) {
                is SocketTimeoutException -> ErrorBean(
                    CODE_ERROR_CONNECT,
                    e.message ?: "",
                    "网络连接超时"
                )
                is ConnectException->ErrorBean(
                    CODE_ERROR_CONNECT,
                    e.message ?: "",
                    "网络连接失败,请检查您的网络设置"
                )
                else -> ErrorBean(CODE_ERROR_OTHER, e.message ?: "", "其他异常")
            }
        }

        //后台返回的异常状态码
        fun <T> getErrorBean(result: BaseResponse<T>) =
            ErrorBean(result.status, result.text, result.text)

        fun getDownloadErrorBean() = ErrorBean(CODE_ERROR_DOWNLOAD, "下载失败", "下载失败")

        fun getTimeErrorBean() = ErrorBean(CODE_ERROR_CONNECT,"获取时间失败","获取时间失败")
    }
}