package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    val asteroids = database.getAsteroidList()

//    private val _asteroidNetworkData = MutableLiveData<String>()
//
//    val asteroidNetworkData: LiveData<String>
//    get() = _asteroidNetworkData

    init {
//        getAsteroidNetworkData()
        insertSampleAsteroidList()

    }

//    private fun getAsteroidNetworkData() {
//        _asteroidNetworkData.value = "Retrieved asteroid data goes here!"
//    }

    private fun insertSampleAsteroidList() =
        viewModelScope.launch(Dispatchers.IO) {
            listOf(
                Asteroid(
                    0L, "Theda", "January 2", 2.45,
                    4.3, 5.3, 300.0, true
                ),
                Asteroid(
                    1L, "Gabrielle", "June 22", 4.2,
                    3.4, 5.55, 500.0, false
                ),
                Asteroid(
                    2L, "Jade", "May 20", 45.3, 400.2,
                    76.1, 1000.0, true
                )
            )
                .apply {
                    val existingList = database.getAsteroidListInstance()
                    if (existingList.isEmpty()) {
                        this.forEach {
                            database.insert(it)
                        }
                    } else {
                        this.forEach {
                            database.update(it)
                        }
                    }
                }
        }

    private val _navigateToDetail = MutableLiveData<Asteroid>()
    val navigateToDetail
    get() = _navigateToDetail

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onNavigateToDetailCompleted() {
        _navigateToDetail.value = null
    }
}


