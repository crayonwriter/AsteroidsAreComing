package com.crayonwriter.asteroidsarecoming.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.api.*
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
    //private val nasaApi = retrofit.getClient().create(NasaApi::class.java)
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }
//    suspend fun getAsteroids(startDate: String, endDate: String): List<Asteroid> =
//        parseAsteroidsJsonResult(JSONObject(nasaApi.getAsteroids(startDate, endDate)))

    //To refresh the offline cache
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = asteroidService.getAsteroids
                parseAsteroidsJsonResult(JSONObject(response.body()))
                database.asteroidDatabaseDao.insertAll(asteroids.asDatabaseModel())
            } catch (e: Exception) {
                Log.e("MainViewModel", "The world is ending!!")
                e.printStackTrace()
            }
        }
    }
}