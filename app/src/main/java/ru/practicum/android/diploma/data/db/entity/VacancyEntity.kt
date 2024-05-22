package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.VacancyDbConverter

@Entity(tableName = "vacancy_table")
@TypeConverters(VacancyDbConverter::class)
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val area: String,
    val employer: String?,
    val employment: String?,
    val salary: String?
)
