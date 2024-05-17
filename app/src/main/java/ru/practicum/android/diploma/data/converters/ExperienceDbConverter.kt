package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Experience

object ExperienceDbConverter {

    @TypeConverter
    fun fromExperienceEntity(data: String): Experience? {
        return Gson().fromJson(data, Experience::class.java)
    }

    @TypeConverter
    fun toExperienceEntity(experience: Experience?): String {
        return Gson().toJson(experience)
    }
}
