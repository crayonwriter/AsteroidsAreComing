package com.crayonwriter.asteroidsarecoming.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.api.AsteroidApi
import com.crayonwriter.asteroidsarecoming.api.Network
import com.crayonwriter.asteroidsarecoming.api.nasaApi
import com.crayonwriter.asteroidsarecoming.api.parseAsteroidsJsonResult
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import com.crayonwriter.asteroidsarecoming.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Repository for getting and saving asteroids
class AsteroidRepository(private val database: AsteroidDatabase) {
    private val nasaApi = retrofit.getClient().create(NasaApi::class.java)
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }
    suspend fun getAsteroids(startDate: String, endDate: String): List<Asteroid> =
        parseAsteroidsJsonResult(JSONObject(nasaApi.getAsteroids(startDate, endDate)))

    //To refresh the offline cache
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

            val asteroidList = Network.asteroids.getAsteroidList().await()
            AsteroidApi.retrofitService.getProperties().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {


                        parseAsteroidsJsonResult(JSONObject(response.body()))

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }

            })
            database.asteroidDatabaseDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }


}