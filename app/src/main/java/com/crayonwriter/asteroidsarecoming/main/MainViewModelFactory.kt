package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao

class MainViewModelFactory(
    private val dataSource: AsteroidDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
        override fun <T: ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
