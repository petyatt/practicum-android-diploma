package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Area

object AreaDbConverter {

    @TypeConverter
    fun fromAreaEntity(data: String): Area {
        return Gson().fromJson(data, Area::class.java)
    }

    @TypeConverter
    fun toAreaEntity(area: Area): String {
        return Gson().toJson(area)
    }
}
