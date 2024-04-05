package com.hobarb.molio.services

import com.hobarb.molio.models.SaveTitleModel
import com.hobarb.molio.network.RetrofitClient
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
}