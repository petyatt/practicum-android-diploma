package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salaryMin: Int?,
    val salaryMax: Int?,
    val currency: String,
    val companyName: String,
    val companyAddress: String,
    val companyLogo: String,
    val experience: String,
    val description: String,
    val employment: String,
    val skills: String,
    val contactName: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactComment: String
)
