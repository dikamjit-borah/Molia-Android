package com.hobarb.molia.services

import TitleDetails
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.hobarb.molia.BuildConfig
import com.hobarb.molia.models.dtos.SaveTitleModel
import com.hobarb.molia.models.schemas.SearchedTitle
import com.hobarb.molia.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHandler {

    fun saveTitle(
        body: SaveTitleModel,
        loadingCallback: (Boolean) -> Unit,
        callback: (Boolean, String, String?) -> Unit
    ) {
        loadingCallback(true)
        RetrofitClient.moliaApiService.saveTitle(body).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                loadingCallback(false)
                when (val apiResponse = ResponseParser.parseResponse(response)) {
                    is ApiResponse.Success -> {
                        val message = response.body()?.get("message")?.asString
                        callback(true, response.message(), message)
                    }

                    is ApiResponse.Error -> {
                        callback(false, apiResponse.errorMessage, null)
                    }

                    else -> {}
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                loadingCallback(false)
                callback(false, t.message!!, null)
            }
        })
    }

    fun searchTitles(
        s: String,
        loadingCallback: (Boolean) -> Unit,
        callback: (Boolean, String, List<SearchedTitle>?) -> Unit
    ) {
        loadingCallback(true)
        RetrofitClient.omdbApiService.searchTitles(BuildConfig.OMDB_API_KEY, s)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>, response: Response<JsonObject>
                ) {
                    loadingCallback(false)
                    when (val apiResponse = ResponseParser.parseResponse(response)) {
                        is ApiResponse.Success -> {
                            val Search: List<SearchedTitle>? = Gson().fromJson(
                                response.body()?.getAsJsonArray("Search"),
                                object : TypeToken<List<SearchedTitle>>() {}.type
                            )
                            callback(true, response.message(), Search)
                        }

                        is ApiResponse.Error -> {
                            callback(false, apiResponse.errorMessage, null)
                        }

                        else -> {}
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    loadingCallback(false)
                    callback(false, t.message!!, null)
                }
            })
    }

    fun fetchTitleDetails(
        i: String,
        loadingCallback: (Boolean) -> Unit,
        callback: (Boolean, String?, TitleDetails?) -> Unit
    ) {
        loadingCallback(true)
        RetrofitClient.omdbApiService.fetchTitleDetails(BuildConfig.OMDB_API_KEY, i)
            .enqueue(object : Callback<TitleDetails> {
                override fun onResponse(
                    call: Call<TitleDetails>, response: Response<TitleDetails>
                ) {
                    loadingCallback(false)
                    callback(true, response.message(), response.body())
                }

                override fun onFailure(call: Call<TitleDetails>, t: Throwable) {
                    loadingCallback(false)
                    callback(true, t.message, null)
                }
            })

    }

    fun fetchSubCollections(
        user_id: String,
        loadingCallback: (Boolean) -> Unit,
        callback: (Boolean, String?, JsonArray?) -> Unit
    ) {
        loadingCallback(true)
        RetrofitClient.moliaApiService.fetchSubCollections(user_id)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>, response: Response<JsonObject>
                ) {
                    loadingCallback(false)
                    when (val apiResponse = ResponseParser.parseResponse(response)) {
                        is ApiResponse.Success -> {

                            val message = response.body()?.get("message")?.asString
                            val subCollections = response.body()?.getAsJsonArray("data")
                            callback(true, message, subCollections)
                        }

                        is ApiResponse.Error -> {
                            callback(false, apiResponse.errorMessage, null)
                        }

                        else -> {}
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    loadingCallback(false)
                    callback(true, t.message, null)
                }
            })

    }
}