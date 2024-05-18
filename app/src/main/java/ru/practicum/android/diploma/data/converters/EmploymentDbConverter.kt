package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Employment

object EmploymentDbConverter {

    @TypeConverter
    fun fromEmploymentEntity(data: String?): Employment? {
        return Gson().fromJson(data, Employment::class.java)
    }

    @TypeConverter
    fun toEmploymentEntity(employment: Employment?): String {
        return Gson().toJson(employment)
    }
}
