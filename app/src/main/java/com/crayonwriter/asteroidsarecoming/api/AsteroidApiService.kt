package com.crayonwriter.asteroidsarecoming.api

import com.crayonwriter.asteroidsarecoming.database.Asteroid
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

//This is the network layer. It is the api that the viewmodel will use to communicate with a web service.

private const val BASE_URL = "https://api.nasa.gov/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService {
    @GET("neo/rest/v1/feed?api_key=DEMO_KEY")
    fun getProperties():
            Call<String>
}

//Later, just call AsteroidApi.retrofitService will return a retrofit object that implements AsteroidApiService
object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}