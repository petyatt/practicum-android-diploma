package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.domain.models.Area

class AreasConverter {
    fun convert(dto: CountryDto) = Area(dto.id, dto.name)

    fun convert(dto: AreaDto, parentId: String?, parentName: String?) =
        Area(dto.id, dto.name, if (parentId != null && parentName != null) Area(parentId, parentName) else null)
}
