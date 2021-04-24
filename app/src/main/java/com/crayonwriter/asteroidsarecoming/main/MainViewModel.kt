package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import android.graphics.Picture
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.api.AsteroidApi
import com.crayonwriter.asteroidsarecoming.api.PictureOfDayApi
import com.crayonwriter.asteroidsarecoming.api.PictureOfDayApiService
import com.crayonwriter.asteroidsarecoming.api.parseAsteroidsJsonResult
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabase.Companion.getDatabase
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao
import com.crayonwriter.asteroidsarecoming.database.DatabaseAsteroid
import com.crayonwriter.asteroidsarecoming.network.AsteroidRepository
import com.crayonwriter.asteroidsarecoming.picture.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class MainViewModel(
//    application: Application) : AndroidViewModel(application)
    val databaseDao: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application)
{

//    private val nasaApi = retrofit.getClient().create(NasaApi::class.java)
    val asteroids = databaseDao.getAsteroidList()
    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    //MutableLiveData and LiveData for the asteroid data
    private val _asteroidNetworkResponse = MutableLiveData<String>()
    val asteroidNetworkResponse: LiveData<String>
        get() = _asteroidNetworkResponse

    //Init block
    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()

        }
       // getAsteroidNetworkResponse()
        getPictureOfDayResponse()
//        insertDataFromNetwork()
        //insertSampleAsteroidList()
    }

    val asteroidList = asteroidRepository.asteroids
//    private fun getAsteroidNetworkResponse() {
//        AsteroidApi.retrofitService.getProperties().enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                if (response.body() != null) {
//                    val asteroids = parseAsteroidsJsonResult(JSONObject(response.body()))
//                    viewModelScope.launch(Dispatchers.IO) {
//                        databaseDao.insertList(asteroids)
//                    }
//                } else {
//                    _asteroidNetworkResponse.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                _asteroidNetworkResponse.value = "Failure " + t.message
//            }
//
//        })
//    }

    //MutableLiveData and LiveData for the Image of the day
    private val _picOfDayResponse = MutableLiveData<String>()
    val picOfDayResponse: LiveData<String>
        get() = _picOfDayResponse

    //MutableLiveData and LiveData for the pictureoftheday's property
    private val _property = MutableLiveData<PictureOfDay>()
    val property: LiveData<PictureOfDay>
        get() = _property

    private fun getPictureOfDayResponse() {
        viewModelScope.launch() {
            try {
                val result = withContext(Dispatchers.IO) {
                    PictureOfDayApiService.getPictureOfDay()
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


