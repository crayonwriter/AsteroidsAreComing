package com.crayonwriter.asteroidsarecoming

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

//Annotate this class to make it a table for DAO and give it a new name

@Parcelize

//Annotate this class to make it a table for DAO and give it a new name
@Entity(tableName = "asteroid_table")
data class Asteroid(val id: Long = 0L, val codename: String, val closeApproachDate: String,
                               val absoluteMagnitude: Double, val estimatedDiameter: Double,
                               val relativeVelocity: Double, val distanceFromEarth: Double,
                               val isPotentiallyHazardous: Boolean) : Parcelable