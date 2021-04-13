package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crayonwriter.asteroidsarecoming.api.AsteroidApi
import com.crayonwriter.asteroidsarecoming.api.PictureOfDayApi
import com.crayonwriter.asteroidsarecoming.api.PictureOfDayApiService
import com.crayonwriter.asteroidsarecoming.api.parseAsteroidsJsonResult
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao
import com.crayonwriter.asteroidsarecoming.database.DatabaseAsteroid
import com.crayonwriter.asteroidsarecoming.picture.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val asteroids = database.getAsteroidList()


    //MutableLiveData and LiveData for the asteroid data
    private val _asteroidNetworkResponse = MutableLiveData<String>()
    val asteroidNetworkResponse: LiveData<String>
        get() = _asteroidNetworkResponse

    //Init block
    init {
        getAsteroidNetworkResponse()
        getPictureOfDayResponse()
//        insertDataFromNetwork()
        //insertSampleAsteroidList()
    }

    private fun getAsteroidNetworkResponse() {
        AsteroidApi.retrofitService.getProperties().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.body() != null) {
                    val asteroids = parseAsteroidsJsonResult(JSONObject(response.body()))
                    viewModelScope.launch(Dispatchers.IO) {
                        database.insertList(asteroids)
                    }
                } else {
                    _asteroidNetworkResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _asteroidNetworkResponse.value = "Failure " + t.message
            }

        })
    }

    //MutableLiveData and LiveData for the Image of the day
    private val _picOfDayResponse = MutableLiveData<String>()
    val picOfDayResponse: LiveData<String>
        get() = _picOfDayResponse

    //MutableLiveData and LiveData for the pictureoftheday's property
    private val _property = MutableLiveData<PictureOfDay>()
    val property: LiveData<PictureOfDay>
        get() = _property

    private fun getPictureOfDayResponse() {
        viewModelScope.launch {
            var getPropertiesDeferred = PictureOfDayApi.retrofitService.getPictureOfDay().toString()
            try {
                var imageFromNetwork = getPropertiesDeferred.await()
                _property.value = imageFromNetwork
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

//    private fun insertSampleAsteroidList() =
//        viewModelScope.launch(Dispatchers.IO) {
//            listOf(
//                Asteroid(
//                    0L, "Theda", "January 2", 2.45,
//                    4.3, 5.3, 300.0, true
//                ),
//                Asteroid(
//                    1L, "Gabrielle", "June 22", 4.2,
//                    3.4, 5.55, 500.0, false
//                ),
//                Asteroid(
//                    2L, "Jade", "May 20", 45.3, 400.2,
//                    76.1, 1000.0, true
//                )
//            )
//                .apply {
//                    val existingList = database.getAsteroidListInstance()
//                    if (existingList.isEmpty()) {
//                        this.forEach {
//                            database.insert(it)
//                        }
//                    } else {
//                        this.forEach {
//                            database.update(it)
//                        }
//                    }
//                }
//        }

//    private fun insertDataFromNetwork() {
//        viewModelScope.launch(Dispatchers.IO) {
//            apply {
//                    val existingList = database.getAsteroidListInstance()
//                    if (existingList.isEmpty()) {
//                            database.insertList(asteroidList)
//                        } else {
//                            database.update(asteroid = Asteroid)
//                        }
//                    }
//                }
//
//        }
//    }



