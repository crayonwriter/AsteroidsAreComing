package com.crayonwriter.asteroidsarecoming.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsteroidDatabaseDao {
    @Insert
    fun insert(asteroid: Asteroid)

    @Update
    fun update(asteroid: Asteroid)

    @Query("SELECT * from asteroid_table WHERE asteroidId = :key")
    fun get(key: Long): Asteroid

    //Return all the rows in the database
    @Query("SELECT * from asteroid_table ORDER BY asteroidId DESC")
    fun getAsteroidList(): LiveData<List<Asteroid>>

    @Query("SELECT * from asteroid_table")
    fun getAsteroidListInstance(): List<Asteroid>

    @Insert
    fun insertList(asteroidList: List<Asteroid>)



}