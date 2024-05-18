package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Employer

object EmployerDbConverter {

    @TypeConverter
    fun fromEmployerEntity(data: String?): Employer? {
        return Gson().fromJson(data, Employer::class.java)
    }

    @TypeConverter
    fun toEmployerEntity(employer: Employer?): String {
        return Gson().toJson(employer)
    }
}
