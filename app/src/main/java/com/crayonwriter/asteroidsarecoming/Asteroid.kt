package com.crayonwriter.asteroidsarecoming

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//TODO: Annotate this class to make it a table for DAO
@Parcelize
data class Asteroid(val id: Long = 0L, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable