package ru.practicum.android.diploma.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyListAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<VacancyItemViewHolder>() {

    var vacancyList = ArrayList<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyItemViewHolder {

        return VacancyItemViewHolder(parent, clickListener)
    }
    override fun getItemCount() = vacancyList.size
    override fun onBindViewHolder(holder: VacancyItemViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }

    fun interface VacancyClickListener {
        fun onClick(vacancy: Vacancy)
    }

    fun setData(vacancy: List<Vacancy>) {
        vacancyList.clear()
        vacancyList.addAll(vacancy)
        notifyDataSetChanged()
    }
}
