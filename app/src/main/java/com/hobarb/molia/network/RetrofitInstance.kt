package com.hobarb.molia.network

import MoliaBackendService
import OmdbApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

    private const val BASE_URL_MOLIA = "https://molia-backend.onrender.com"
   // private const val BASE_URL_OMDB = "https://omdb.onrender.com"
   private const val BASE_URL_OMDB = "https://www.omdbapi.com"

    val moliaApiService: MoliaBackendService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_MOLIA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MoliaBackendService::class.java)
    }

    val omdbApiService: OmdbApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_OMDB)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()))
            .build()

        retrofit.create(OmdbApiService::class.java)
    }
}
