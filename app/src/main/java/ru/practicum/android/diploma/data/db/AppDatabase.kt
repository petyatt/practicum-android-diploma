package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
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
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Database(entities = [VacancyEntity::class], version = 1)
@TypeConverters(
    VacancyDbConverter::class, ContactsDbConverter::class, AddressDbConverter::class,
    EmploymentDbConverter::class, EmployerDbConverter::class, ExperienceDbConverter::class,
    SalaryDbConverter::class, ScheduleDbConverter::class, AreaDbConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
