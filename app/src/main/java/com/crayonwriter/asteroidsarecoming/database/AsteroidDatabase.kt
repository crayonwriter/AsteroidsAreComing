package com.crayonwriter.asteroidsarecoming.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crayonwriter.asteroidsarecoming.Asteroid

//Database class: one entity from the one table, version #,
//and not a complex database so no need for exportSchema to be true
@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    //Companion object to allow access to the methods for
    //creating or getting a database without instantiating the class
    companion object{
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null
        //Return a reference to the Asteroid Database. It needs
        //a context because the database builder needs a context
        fun getInstance(context: Context) : AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE

                //Check if already a database. If not, create it.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}