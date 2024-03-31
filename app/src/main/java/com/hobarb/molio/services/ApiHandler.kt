package com.hobarb.molio.services

import android.widget.Toast
import com.hobarb.molio.models.SaveTitleModel
import com.hobarb.molio.network.RetrofitClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHandler {

    fun saveTitle(body: SaveTitleModel){
        RetrofitClient.moliaApiService.saveTitle(body).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                print(response)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                // Handle network errors
                print("here")
                print(t.message)
            }
        })
    }
}