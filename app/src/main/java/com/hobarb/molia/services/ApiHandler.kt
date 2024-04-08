package com.hobarb.molia.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.hobarb.molia.models.dtos.SaveTitleModel
import com.hobarb.molia.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHandler {

    fun saveTitle(body: SaveTitleModel, callback: (Boolean, String, Unit?) -> Unit) {
        RetrofitClient.moliaApiService.saveTitle(body).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                when (val apiResponse = ResponseParser.parseResponse(response)) {
                    is ApiResponse.Success -> {
                        callback(true, "Success", apiResponse.data)
                    }
                    is ApiResponse.Error -> {
                        callback(false, apiResponse.errorMessage, null)
                    }

                    else -> {callback(false, "Some error", null)}
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                // Handle network errors
                callback(false, "Error: ${t.message}", null)
            }
        })
    }
    fun searchTitles(s: String, callback: (Boolean, String?, JsonArray?) -> Unit) {
        RetrofitClient.omdbApiService.searchTitles("", s).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                callback(true, response.message(), response.body()?.getAsJsonArray("Search"))
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                callback(true, t.message, null)
            }
        })
    }
}