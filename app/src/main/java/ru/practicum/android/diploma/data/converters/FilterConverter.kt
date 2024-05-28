package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.domain.models.Filter

class FilterConverter {
    fun convert(filter: Filter?): Map<String, String> {
        if (filter == null) return emptyMap()
        val map = mutableMapOf<String, String>()
        with(filter) {
            area?.run { map[AREA] = area.id }
            industry?.run { map[INDUSTRY] = industry.id }
            salary?.run { map[SALARY] = salary.toString() }
            map[ONLY_WITH_SALARY] = onlyWithSalary.toString()
        }
        return map
    }

    companion object {
        const val AREA = "area"
        const val INDUSTRY = "industry"
        const val SALARY = "salary"
        const val ONLY_WITH_SALARY = "only_with_salary"
    }
}
