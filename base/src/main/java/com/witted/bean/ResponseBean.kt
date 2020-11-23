package com.witted.bean

//接口返回数据类型
data class BaseResponse<T>(
    var status: Int,
    var text: String,
    var result: T
)

//异常的数据类型
data class ErrorBean(
    var status: Int,
    var msg: String,
    var errorDesc: String
)

class BaseRequestBody {
    var devID: String? = null
    var log: String? = null
    var level: Int = 60
}

class DeviceLog {
    var date: String = ""   //日期
    var ssid: String? = ""       //WIFI名称
    var mac: String = ""  //MAC地址
    var channel: String? = ""    //信道
    var rssi: String? = ""       //信号强度
    var version: String = ""    //当前版本
    var wlan: Boolean = false   //WLAN连接状态
    var ip: String = ""         //IP地址
    var serial: String = ""     //设备序列号
}
