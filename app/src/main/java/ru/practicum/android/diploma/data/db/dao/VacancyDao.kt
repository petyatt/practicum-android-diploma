package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancies(): List<VacancyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancyEntity: VacancyEntity)

    @Query("DELETE FROM vacancy_table WHERE id = :vacancyId")
    suspend fun removeVacancy(vacancyId: String)

    @Query("SELECT * FROM vacancy_table WHERE id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity?
}
