package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country

class AreasConverter {
    fun convert(dto: CountryDto) = Country(dto.id, dto.name)

    fun convert(dto: AreaDto) = Area(dto.id, dto.name)
}
