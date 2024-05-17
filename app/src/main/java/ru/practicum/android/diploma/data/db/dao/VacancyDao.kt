package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.AddressDbConverter
import ru.practicum.android.diploma.data.converters.AreaDbConverter
import ru.practicum.android.diploma.data.converters.ContactsDbConverter
import ru.practicum.android.diploma.data.converters.EmployerDbConverter
import ru.practicum.android.diploma.data.converters.EmploymentDbConverter
import ru.practicum.android.diploma.data.converters.ExperienceDbConverter
import ru.practicum.android.diploma.data.converters.SalaryDbConverter
import ru.practicum.android.diploma.data.converters.ScheduleDbConverter
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity

@Dao
@TypeConverters(
    VacancyDbConverter::class, ContactsDbConverter::class, AddressDbConverter::class,
    EmploymentDbConverter::class, EmployerDbConverter::class, ExperienceDbConverter::class,
    SalaryDbConverter::class, ScheduleDbConverter::class, AreaDbConverter::class
)
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancies(): List<VacancyDescriptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancyDescriptionEntity: VacancyDescriptionEntity)

    @Query("DELETE FROM vacancy_table WHERE id = :vacancyId")
    suspend fun removeVacancy(vacancyId: String)

    @Query("SELECT * FROM vacancy_table WHERE id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyDescriptionEntity?
}
