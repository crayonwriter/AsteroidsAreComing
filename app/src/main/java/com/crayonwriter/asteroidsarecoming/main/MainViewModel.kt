package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.api.*
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase.Companion.getDatabase
import com.crayonwriter.asteroidsarecoming.network.AsteroidRepository
import com.crayonwriter.asteroidsarecoming.picture.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(
    application: Application
) : AndroidViewModel(application)
{
        private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    val asteroids = asteroidRepository.asteroids

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            getPictureOfDayResponse()
        }
         }

    //MutableLiveData and LiveData for the pictureoftheday's property
    private val _property = MutableLiveData<PictureOfDay>()
    val property: LiveData<PictureOfDay>
        get() = _property

    private fun getPictureOfDayResponse() {
        viewModelScope.launch() {
            try {
                val result = withContext(Dispatchers.IO) {
                    Network.pictureOfDay.getPictureOfDay()
                }
                _property.value = result

            } catch (e: Exception) {
                Log.e("MainViewModel", "Bad stuff is happening!")
                e.printStackTrace()
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


