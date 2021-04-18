package com.crayonwriter.asteroidsarecoming.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.crayonwriter.asteroidsarecoming.api.Network
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import com.crayonwriter.asteroidsarecoming.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Repository for getting and saving asteroids
class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }

    //To refresh the offline cache
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidList = Network.asteroids.getAsteroidList().await()
            database.asteroidDatabaseDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }
}