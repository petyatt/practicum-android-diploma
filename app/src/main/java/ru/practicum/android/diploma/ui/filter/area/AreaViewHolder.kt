package ru.practicum.android.diploma.ui.filter.area

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class AreaViewHolder(view: View) : ViewHolder(view) {
    private val areaView = view.findViewById<TextView>(R.id.area)

    fun bind(model: Area) {
        areaView.text = model.name
    }
}
