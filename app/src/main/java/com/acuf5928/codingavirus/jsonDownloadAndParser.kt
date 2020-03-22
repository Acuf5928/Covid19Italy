package com.acuf5928.codingavirus

import android.util.Log
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MyJsonParser {
    interface Result {
        fun onComplete(json: MutableList<JSONObject>)
        fun onError(e: Exception)
    }

    fun start(url: String, callback: Result) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("APP", "error: $e")
                callback.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val result = mutableListOf<JSONObject>()
                    try {
                        val resp = response.body!!.string()
                        val json = JSONArray(resp).let {
                            for (index in 0 until it.length()) {
                                result.add(JSONObject(it.get(index).toString()))
                            }
                        }
                        callback.onComplete(result)
                    } catch (e: Exception) {
                        Log.e("APP", "error: $e")
                        callback.onError(e)
                    }
                }
            }
        })
    }
}