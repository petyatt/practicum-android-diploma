package ru.practicum.android.diploma.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.model.CURRENCY_SYMBOLS
import ru.practicum.android.diploma.ui.model.ScreenState
import java.util.Locale

class VacancyViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)) {

    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyTitle = itemView.findViewById<TextView>(R.id.company_title)
    private val imageCompany = itemView.findViewById<ImageView>(R.id.image_company)

    private val noSalary = itemView.findViewById<TextView>(R.id.no_salary)
    private val salaryMin = itemView.findViewById<Group>(R.id.salary_min)
    private val salaryMinVal = itemView.findViewById<TextView>(R.id.salary_min_val)
    private val salaryMax = itemView.findViewById<Group>(R.id.salary_max)
    private val salaryMaxVal = itemView.findViewById<TextView>(R.id.salary_max_val)
    private val currency = itemView.findViewById<TextView>(R.id.currency)

    private val vacancyGroup = itemView.findViewById<ConstraintLayout>(R.id.vacancy)
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

    private val topItem = itemView.findViewById<View>(R.id.top_item)

    fun bind(state: ScreenState<Vacancy>) {
        when (state) {
            is ScreenState.Loaded -> bind(state.t)
            is ScreenState.Option<*, *> -> bindTop(state.value as Boolean)
            else -> bindLoading()
        }
    }

    private fun bindLoading() {
        topItem.isVisible = false
        vacancyGroup.isVisible = false
        progressBar.isVisible = true
    }

    private fun bindTop(visible: Boolean) {
        vacancyGroup.isVisible = false
        progressBar.isVisible = false
        topItem.isVisible = visible
    }

    private fun bind(vacancy: Vacancy) {
        topItem.isVisible = false
        progressBar.isVisible = false
        vacancyGroup.isVisible = true

        vacancyTitle.text = vacancy.name
        companyTitle.text = vacancy.companyName
        Glide.with(itemView)
            .load(vacancy.companyLogo)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_small_2)))
            .into(imageCompany)

        salaryMin.isVisible = vacancy.salaryMin != null
        salaryMinVal.text = String.format(Locale.forLanguageTag("ru"), "%,d", vacancy.salaryMin)
        salaryMax.isVisible = vacancy.salaryMax != null
        salaryMaxVal.text = String.format(Locale.forLanguageTag("ru"), "%,d", vacancy.salaryMax)
        currency.isVisible = vacancy.salaryMin != null || vacancy.salaryMax != null
        currency.text = CURRENCY_SYMBOLS[vacancy.currency] ?: ""
        noSalary.isVisible = vacancy.salaryMin == null && vacancy.salaryMax == null
    }

}
