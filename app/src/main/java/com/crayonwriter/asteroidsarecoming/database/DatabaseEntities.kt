package com.crayonwriter.asteroidsarecoming.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//Create the DatabaseEntities class, adding annotations for the class and the primary key.
//Create the DatabaseAsteroid database object. Create a Room @Entity called DatabaseAndroid

@Entity(tableName = "asteroidList_table")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val asteroidId: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            asteroidId = it.asteroidId,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}