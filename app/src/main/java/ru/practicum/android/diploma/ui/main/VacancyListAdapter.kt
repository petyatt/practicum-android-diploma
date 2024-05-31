package ru.practicum.android.diploma.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.model.ScreenState

class VacancyListAdapter(
    private val vacancies: Vacancies,
    private val onClick: ((Vacancy) -> Unit)?,
    private val onNeedPage: ((Int) -> Unit)? = null
) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    private val vacancyList = vacancies.items.toMutableList()
    private var page = 0
    private var ok = true

    fun load(state: ScreenState<Vacancies>) {
        val prevOk = ok
        ok = state is ScreenState.Loaded
        if (ok) load((state as ScreenState.Loaded).t)
        if (prevOk && !ok) notifyDataSetChanged()
    }

    private fun load(vacancies: Vacancies) {
        if (vacancies.page <= page) return
        page = vacancies.page
        vacancyList.addAll(vacancies.items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VacancyViewHolder(parent)

    override fun getItemCount() =
        if (vacancyList.size < vacancies.found && ok) vacancyList.size + 1 else vacancyList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        if (vacancyList.size - PREREAD_HOLDER == position && vacancyList.size < vacancies.found) {
            onNeedPage?.run { onNeedPage.invoke(page + 1) }
        }
        if (position < vacancyList.size) {
            holder.bind(ScreenState.Loaded(vacancyList[position]))
            holder.itemView.setOnClickListener { onClick?.run { onClick.invoke(vacancyList[position]) } }
        } else {
            holder.bind(ScreenState.Loading())
        }
    }

    companion object {
        private const val PREREAD_HOLDER = 1
    }
}
