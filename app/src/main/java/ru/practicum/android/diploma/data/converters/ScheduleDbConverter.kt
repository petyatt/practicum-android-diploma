package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Schedule

object ScheduleDbConverter {

    @TypeConverter
    fun fromScheduleEntity(data: String): Schedule? {
        return Gson().fromJson(data, Schedule::class.java)
    }

    @TypeConverter
    fun toScheduleEntity(schedule: Schedule?): String {
        return Gson().toJson(schedule)
    }
}
