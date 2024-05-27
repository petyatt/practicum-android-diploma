package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.AreaDto

class AreasResponse(
    val areas: List<AreaDto>,
    val id: String,
    val name: String
) : Response()
