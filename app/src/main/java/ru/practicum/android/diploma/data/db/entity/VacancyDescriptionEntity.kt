package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.VacancyDbConverter

@Entity(tableName = "vacancy_table")
@TypeConverters(VacancyDbConverter::class)
data class VacancyDescriptionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String,
    val employer: String,
    val area: String,
    val url: String,
    val address: String,
    val experience: String,
    val employment: String,
    val schedule: String,
    val contacts: String,
    val description: String,
)
