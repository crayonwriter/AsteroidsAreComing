package com.crayonwriter.asteroidsarecoming.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.crayonwriter.asteroidsarecoming.database.AsteroidDatabaseDao

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application) : AndroidViewModel(application) {
}