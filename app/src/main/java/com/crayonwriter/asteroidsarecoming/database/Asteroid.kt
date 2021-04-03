package com.crayonwriter.asteroidsarecoming.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Annotate this class to make it a table for DAO and give it a new name

@Parcelize

@Entity(tableName = "asteroid_table")
data class Asteroid(
    //Annotate each column and give each a custom name, except for the PrimaryKey
    @PrimaryKey() val asteroidId: Long,
    @ColumnInfo(name = "code_name") val codename: String,
    @ColumnInfo(name = "close_approach_date") val closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude") val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter") val estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity") val relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth") val distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous") val isPotentiallyHazardous: Boolean) : Parcelable

