package com.crayonwriter.asteroidsarecoming.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsteroidDatabaseDao {
    @Insert
    fun insert(asteroid: DatabaseAsteroid)

    @Update
    fun update(asteroid: DatabaseAsteroid)

    @Query("SELECT * from asteroid_table WHERE asteroidId = :key")
    fun get(key: Long): DatabaseAsteroid

    //Return all the rows in the database
    @Query("SELECT * from asteroid_table ORDER BY asteroidId DESC")
    fun getAsteroidList(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * from asteroid_table")
    fun getAsteroidListInstance(): List<DatabaseAsteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroidList: List<DatabaseAsteroid>)

//Queries regarding DatabaseAsteroid
    @Query("select * from asteroid_table")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg asteroids: DatabaseAsteroid)

}