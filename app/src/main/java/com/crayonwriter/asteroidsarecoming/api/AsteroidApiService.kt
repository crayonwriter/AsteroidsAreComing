package com.crayonwriter.asteroidsarecoming.api

import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.network.NetworkAsteroidContainer
import com.crayonwriter.asteroidsarecoming.picture.PictureOfDay
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//This is the network layer. It is the api that the viewmodel will use to communicate with a web service.

private const val BASE_URL = "https://api.nasa.gov/"

interface AsteroidService {
    @GET("neo/rest/v1/feed?api_key=DEMO_KEY")
    suspend fun getAsteroids(@Query("start_date") startDate: String,
                             @Query("end_date") endDate: String): String
}

interface PictureOfDayApiService {
    @GET("planetary/apod?api_key=DEMO_KEY")
    suspend fun getPictureOfDay() : PictureOfDay
}

object Network {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val pictureOfDay: PictureOfDayApiService = retrofit.create(PictureOfDayApiService::class.java)
    val asteroids: AsteroidService = retrofit.create(AsteroidService::class.java)
}


