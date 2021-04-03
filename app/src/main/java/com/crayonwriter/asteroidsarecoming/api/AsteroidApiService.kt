package com.crayonwriter.asteroidsarecoming.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

//This is the network layer. It is the api that the viewmodel will use to communicate with a web service.

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed?"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService {
    @GET("start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY")
    fun getProperties():
            Call<String>
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}