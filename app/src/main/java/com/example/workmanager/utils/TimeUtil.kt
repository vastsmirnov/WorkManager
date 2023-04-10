package com.example.workmanager.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {
        fun getCurrentTime(): String = SimpleDateFormat("HH:mm:ss").format(Date())
    }
}