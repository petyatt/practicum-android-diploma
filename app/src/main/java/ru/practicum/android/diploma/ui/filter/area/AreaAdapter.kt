package ru.practicum.android.diploma.ui.filter.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class AreaAdapter(private val areas: List<Area>, private val onClick: (Area) -> Unit) : Adapter<AreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AreaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_area, parent, false)
    )

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val area = areas[position]
        with(holder) {
            bind(area)
            itemView.setOnClickListener { onClick.invoke(area) }
        }
    }

    override fun getItemCount() = areas.size
}
