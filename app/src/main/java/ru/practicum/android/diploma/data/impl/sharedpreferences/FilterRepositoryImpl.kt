package ru.practicum.android.diploma.data.impl.sharedpreferences

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterRepositoryImpl(
    context: Context,
) : FilterRepository {
    private var sharedPreferences = context.getSharedPreferences(
        KEY_FILTER_SHAREDPREF,
        Context.MODE_PRIVATE
    )

    override suspend fun save(filter: Filter) {
        sharedPreferences.edit().putString(FILTER_KEY, Gson().toJson(filter)).apply()
    }

    override suspend fun get(): Filter? {
        val filterJson = sharedPreferences.getString(FILTER_KEY, null)
        return Gson().fromJson(filterJson, Filter::class.java)
    }

    companion object {
        const val KEY_FILTER_SHAREDPREF = "key_filter_sharedPref"
        const val FILTER_KEY = "filter_key"
    }
}
