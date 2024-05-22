package ru.practicum.android.diploma.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyListAdapter(val vacancyList: MutableList<VacancyDetail>, val onClick: (String) -> Unit = { _ -> run {} }) :
    RecyclerView.Adapter<VacancyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VacancyItemViewHolder(parent)

    override fun getItemCount() = vacancyList.size
    override fun onBindViewHolder(holder: VacancyItemViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener { onClick.invoke(vacancyList[position].id) }
    }
}
