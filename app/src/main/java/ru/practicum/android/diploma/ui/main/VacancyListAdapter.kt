package ru.practicum.android.diploma.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyListBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyListAdapter(var vacancyList: MutableList<Vacancy>) : RecyclerView.Adapter<VacancyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyItemViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyItemViewHolder(ItemVacancyListBinding.inflate(layoutInspector, parent, false))
    }
    override fun getItemCount() = vacancyList.size
    override fun onBindViewHolder(holder: VacancyItemViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }
}
