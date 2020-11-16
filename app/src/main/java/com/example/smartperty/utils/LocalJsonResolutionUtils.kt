package com.example.smartperty.utils

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * 得到json文件中的内容
 * @param context
 * @param fileName
 * @return
 */
fun getJson(context: Context, fileName: String?): String? {
    val stringBuilder = StringBuilder()
    //获得assets资源管理器
    val assetManager: AssetManager = context.getAssets()
    //使用IO流读取json文件内容
    try {
        val bufferedReader = BufferedReader(
            InputStreamReader(
                assetManager.open(fileName!!), "utf-8"
            )
        )
        var line: String? = ""
        while (bufferedReader.readLine().also({ line = it }) != null) {
            stringBuilder.append(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return stringBuilder.toString()
}

/**
 * 将字符串转换为 对象
 * @param json
 * @param type
 * @return
 */
fun <T> JsonToObject(json: String?, type: Class<T>?): T {
    val gson = Gson()
    return gson.fromJson(json, type)
}