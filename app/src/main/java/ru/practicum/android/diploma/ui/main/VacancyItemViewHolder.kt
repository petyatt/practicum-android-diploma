package ru.practicum.android.diploma.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyItemViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy_list, parent, false)) {

    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyTitle = itemView.findViewById<TextView>(R.id.company_title)
    private val imageCompany = itemView.findViewById<ImageView>(R.id.image_company)

    private val noSalary = itemView.findViewById<TextView>(R.id.no_salary)
    private val salaryMin = itemView.findViewById<LinearLayout>(R.id.salary_min)
    private val salaryMinVal = itemView.findViewById<TextView>(R.id.salary_min_val)
    private val salaryMax = itemView.findViewById<LinearLayout>(R.id.salary_max)
    private val salaryMaxVal = itemView.findViewById<TextView>(R.id.salary_max_val)
    private val currency = itemView.findViewById<TextView>(R.id.currency)

    fun bind(vacancy: Vacancy) {
        vacancyTitle.text = vacancy.name
        companyTitle.text = vacancy.companyName
        Glide.with(itemView)
            .load(vacancy.companyLogo)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_small_2)))
            .into(imageCompany)

        salaryMin.isVisible = vacancy.salaryMin != null
        salaryMinVal.text = vacancy.salaryMin?.toString()
        salaryMax.isVisible = vacancy.salaryMax != null
        salaryMaxVal.text = vacancy.salaryMax?.toString()
        currency.isVisible = vacancy.salaryMin != null || salaryMax != null
        currency.text = vacancy.currency
        noSalary.isVisible = vacancy.salaryMin == null && salaryMax == null
    }
}
