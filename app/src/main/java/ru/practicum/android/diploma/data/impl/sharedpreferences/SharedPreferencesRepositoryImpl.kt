package ru.practicum.android.diploma.data.impl.sharedpreferences

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedpreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.util.FILTER_KEY
import ru.practicum.android.diploma.util.KEY_FILTER_SHAREDPREFENCES

class SharedPreferencesRepositoryImpl(
    context: Context,
) : SharedpreferencesRepository {
    private val gson = Gson()
    private var sharedPreferences = context.getSharedPreferences(
        KEY_FILTER_SHAREDPREFENCES,
        Context.MODE_PRIVATE
    )

    override suspend fun save(filter: Filter?) {
        val filterJson = gson.toJson(filter)
        sharedPreferences.edit().apply {
            putString(FILTER_KEY, filterJson)
            apply()
        }
    }

    override fun get(): Filter? {
        val filterJson = sharedPreferences.getString(FILTER_KEY, null)
        return gson.fromJson(filterJson, Filter::class.java)
    }

    override suspend fun clear() {
        sharedPreferences.edit().remove(FILTER_KEY).apply()
    }
}
