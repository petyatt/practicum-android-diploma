package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Salary

object SalaryDbConverter {
    @TypeConverter
    fun fromSalaryEntity(data: String?): Salary? {
        return Gson().fromJson(data, Salary::class.java)
    }

    @TypeConverter
    fun toSalaryEntity(salary: Salary?): String {
        return Gson().toJson(salary)
    }
}
