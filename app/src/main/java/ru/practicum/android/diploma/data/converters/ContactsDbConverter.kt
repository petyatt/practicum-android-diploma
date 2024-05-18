package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Contacts

object ContactsDbConverter {
    @TypeConverter
    fun fromContactsEntity(data: String): Contacts? {
        return Gson().fromJson(data, Contacts::class.java)
    }

    @TypeConverter
    fun toContactsEntity(contacts: Contacts?): String {
        return Gson().toJson(contacts)
    }
}
