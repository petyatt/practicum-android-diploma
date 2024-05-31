package ru.practicum.android.diploma.domain.models

import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.ui.model.Selectable

@Parcelize
data class Area(
    val id: String,
    val name: String,
    val parent: Area? = null
) : Selectable {
    constructor(area: Area, parent: Area? = null) : this(area.id, area.name, parent)

    override val caption: String
        get() = (parent?.let { parent.name + ", " } ?: "") + name
}
