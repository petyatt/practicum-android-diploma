package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Address

object AddressDbConverter {

    @TypeConverter
    fun fromAddressEntity(data: String?): Address? {
        return Gson().fromJson(data, Address::class.java)
    }

    @TypeConverter
    fun toAddressEntity(address: Address?): String {
        return Gson().toJson(address)
    }
}
