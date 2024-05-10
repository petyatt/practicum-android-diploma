package ru.practicum.android.diploma.data.db

import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDao

abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
