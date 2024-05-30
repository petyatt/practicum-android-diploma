package ru.practicum.android.diploma.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.model.ScreenState

class VacancyListAdapter(
    private val vacancies: Vacancies,
    private val onClick: (Vacancy) -> Unit,
    private val onNeedPage: (Int) -> Unit = {}
) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    private val vacancyList = vacancies.items.toMutableList()
    private var page = 0

    init {
        page = vacancies.page
    }

    fun load(vacancies: Vacancies) {
        if (vacancies.page <= page) return
        page = vacancies.page
        vacancyList.addAll(vacancies.items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VacancyViewHolder(parent)

    override fun getItemCount() = if (vacancyList.size < vacancies.found) vacancyList.size + 1 else vacancyList.size
    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        if (vacancyList.size == position && vacancyList.size < vacancies.found) {
            onNeedPage.invoke(page + 1)
        }
        if (position < vacancyList.size) {
            holder.bind(ScreenState.Loaded(vacancyList[position]))
            holder.itemView.setOnClickListener { onClick.invoke(vacancyList[position]) }
        } else {
            holder.bind(ScreenState.Loading())
        }
    }
}
