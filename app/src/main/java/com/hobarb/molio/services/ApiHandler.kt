package com.hobarb.molio.services

import android.widget.Toast
import com.google.gson.JsonObject
import com.hobarb.molio.models.dtos.SaveTitleModel
import com.hobarb.molio.models.schemas.SearchTitle
import com.hobarb.molio.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query

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
    fun searchTitles(s: String, callback: (Boolean, String?, JsonObject?) -> Unit) {
        RetrofitClient.omdbApiService.searchTitles("", s).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                callback(true, response.message(), response.body())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                callback(true, t.message, null)
            }
        })
    }
}