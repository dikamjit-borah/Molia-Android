package com.hobarb.molia.services

import TitleDetails
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.hobarb.molia.BuildConfig
import com.hobarb.molia.models.dtos.SaveTitleModel
import com.hobarb.molia.models.schemas.SearchedTitle
import com.hobarb.molia.network.RetrofitClient
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

                    else -> {
                        callback(false, "Some error", null)
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                // Handle network errors
                callback(false, "Error: ${t.message}", null)
            }
        })
    }

    fun searchTitles(s: String, callback: (Boolean, String?, List<SearchedTitle>?) -> Unit) {
        RetrofitClient.omdbApiService.searchTitles(BuildConfig.OMDB_API_KEY, s)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    val Search: List<SearchedTitle>? = Gson().fromJson(
                        response.body()?.getAsJsonArray("Search"),
                        object : TypeToken<List<SearchedTitle>>() {}.type
                    )

                    callback(true, response.message(), Search)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    callback(true, t.message, null)
                }
            })
    }

    fun fetchTitleDetails(i: String, callback: (Boolean, String?, TitleDetails?) -> Unit) {
        RetrofitClient.omdbApiService.fetchTitleDetails(BuildConfig.OMDB_API_KEY, i)
            .enqueue(object : Callback<TitleDetails> {
                override fun onResponse(
                    call: Call<TitleDetails>,
                    response: Response<TitleDetails>
                ) {
                    callback(true, response.message(), response.body())
                }

                override fun onFailure(call: Call<TitleDetails>, t: Throwable) {
                    callback(true, t.message, null)
                }
            })

    }
}