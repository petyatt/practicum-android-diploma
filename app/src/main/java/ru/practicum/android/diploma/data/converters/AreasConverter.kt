package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.domain.models.Country

class AreasConverter {
    fun convert(dto: CountryDto): Country = Country(dto.id, dto.name)
}
