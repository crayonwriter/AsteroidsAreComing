package com.crayonwriter.asteroidsarecoming.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.crayonwriter.asteroidsarecoming.api.*
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import com.crayonwriter.asteroidsarecoming.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

//Repository for getting and saving asteroids
class AsteroidRepository(private val database: AsteroidDatabase) {
        val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }

    //To refresh the offline cache
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = Network.asteroids.getAsteroids("", "")
                val asteroidsRefreshed = parseAsteroidsJsonResult(JSONObject(response))
                database.asteroidDatabaseDao.insertAll(NetworkAsteroidContainer(asteroidsRefreshed).asDatabaseModel())
            } catch (e: Exception) {
                Log.e("MainViewModel", "The world is ending!!")
                e.printStackTrace()
            }
        }
    }
}