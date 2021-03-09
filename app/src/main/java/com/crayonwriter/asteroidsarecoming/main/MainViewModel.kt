package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.crayonwriter.asteroidsarecoming.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application) : AndroidViewModel(application) {

        val asteroids = database.getAsteroidList()

        init {
            insertSampleAsteroidList()
        }

    private fun insertSampleAsteroidList() =
        listOf(
            Asteroid(0L, "Theda", "January 2", 2.45,
                4.3, 5.3, 300.0, true),
            Asteroid(1L, "Gabrielle", "June 22", 4.2,
                3.4, 5.55, 500.0, false),
            Asteroid(2L, "Jade", "May 20", 45.3, 400.2,
            76.1, 1000.0, true))
    }
