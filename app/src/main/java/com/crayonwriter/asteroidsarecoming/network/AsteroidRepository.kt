package com.crayonwriter.asteroidsarecoming.network

import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Repository for getting and saving asteroids
class AsteroidRepository(private val database: AsteroidDatabase) {

    //To refresh the offline cache
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

        }
    }
}