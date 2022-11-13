package com.example.asteriod.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteriod.Asteroid

//id string
//absolute_magnitude long
//estimated_diameter_max long
//is_potentially_hazardous_asteroid boaeln
//close_approach_data -> relative_velocity -> kilometers_per_second string
//close_approach_data -> miss_distance -> astronomical string
@Entity(tableName = "asteroid")
data class AsteroidDbModel constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,
//estimated_diameter_max
    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,
//is_potentially_hazardous_asteroid
    @ColumnInfo(name = "is_hazardous")
    val isPotentiallyHazardous: Boolean,
//close_approach_data -> relative_velocity -> kilometers_per_second
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,
//close_approach_data -> miss_distance -> astronomical
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double
)



fun List<AsteroidDbModel>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id, codename = it.name, closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude, estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity, distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
