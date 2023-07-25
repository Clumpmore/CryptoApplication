package com.example.cryptoapplication.utils

import android.provider.ContactsContract.Data
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimeStampToTime(timestamp:Int?): String {
    if (timestamp==null) return "null"
    val stamp = Timestamp(timestamp*1000.toLong())
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}