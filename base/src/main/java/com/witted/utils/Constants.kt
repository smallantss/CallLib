package com.witted.utils

import android.os.Environment

//ip 端口
const val IP_PORT = "172.16.1.210:9995"

//SP名字
const val SP_NAME = "sp_door_app"

//正常的返回码
const val CODE_SUCCESS = 200

//连接失败
const val CODE_ERROR_CONNECT = -1

//下载失败
const val CODE_ERROR_DOWNLOAD = -3

//其他异常,如404
const val CODE_ERROR_OTHER = -2

//下载的apk名字
const val APK_NAME = "door.apk"

//日志的文件名
const val LOG_NAME = "log.txt"

const val CALL_CONNECT_NAME = "callConnect.txt"

const val DEVICE_LOG_NAME = "deviceLog.txt"

const val EXCEPTION_NAME = "exception.txt"

const val CALLS_NAME = "calls.txt"

//设备ID和IP
const val DEVICE_ID = "device_id"
const val DEVICE_IP = "device_ip"
const val FIRST_LAUNCH = "first_launch"
const val CALL_IP = "call_ip"
const val ROOM_ID = "room_id"
const val IP = "device_ip"
const val PORT = "device_port"
const val URL = "device_url"

//病人护理等级
const val CARE_LEVEL_0 = 0
const val CARE_LEVEL_1 = 1
const val CARE_LEVEL_2 = 2
const val CARE_LEVEL_3 = 3

//设置页的网络设置和设备编号
const val ID_SETTING_EXIT = -1
const val ID_DEVICE_MANAGER = 0
const val ID_SETTING_NET = 1
const val ID_SETTING_DEVICE = 2
const val ID_SETTING_CALL = 3

//管理员密码
const val ID_PASSWORD = "080900"

//TODO app更新的名字
const val APP_UPGRADE_NAME = "doorScreen"

//首页接口请求间隔 更新接口
const val TIME_INTERVAL_HOME = 5000L
const val TIME_INTERVAL_UPGRADE = 3 * 60 * 1000L

//呼叫状态 来电 接听 挂断
const val STATE_CALL_INCOMING = 0
const val STATE_CALL_RECEIVE = 1
const val STATE_CALL_REFUSE = 2

//倒计时
const val TIME_COUNT = 60

const val BD_TTS_LICENSE = "BD_TTS_LICENSE"

const val ACTION_LAUNCH = "com.witted.START_MONITOR"
const val ACTION_EXIT = "com.witted.STOP_MONITOR"

const val TIME_LIMIT = 5L