package ru.practicum.android.diploma.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyListAdapter(
    val vacancyList: MutableList<Vacancy>, val onClickVacancy: VacancyClick
) : RecyclerView.Adapter<VacancyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VacancyItemViewHolder(parent)

    override fun getItemCount() = vacancyList.size
    override fun onBindViewHolder(holder: VacancyItemViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener {
            onClickVacancy.onClickVacancy(vacancyList[position])
        }
    }

    fun interface VacancyClick {
        fun onClickVacancy(vacancy: Vacancy)
    }
}
