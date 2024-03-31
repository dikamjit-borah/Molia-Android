package com.hobarb.molio.network

import MolioBackendService
import OmdbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL_MOLIA = "https://molia-backend.onrender.com"
    private const val BASE_URL_OMDB = "https://omdb.onrender.com"

    val moliaApiService: MolioBackendService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_MOLIA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MolioBackendService::class.java)
    }

    val omdbApiService: OmdbApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_OMDB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(OmdbApiService::class.java)
    }
}
