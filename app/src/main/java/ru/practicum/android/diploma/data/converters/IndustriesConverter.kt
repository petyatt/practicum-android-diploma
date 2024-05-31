package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesConverter {
    fun convert(dto: IndustryDto): Industry = Industry(dto.id, dto.name)
}
