package ru.practicum.android.diploma.domain.models

import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.ui.model.Selectable

@Parcelize
data class Area(
    val id: String,
    val name: String
) : Selectable {
    override val caption: String
        get() = name
}
