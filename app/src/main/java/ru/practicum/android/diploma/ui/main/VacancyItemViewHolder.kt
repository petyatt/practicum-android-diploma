package ru.practicum.android.diploma.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyItemViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy_list, parent, false)) {

    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyTitle = itemView.findViewById<TextView>(R.id.company_title)
    private val vacancySalary = itemView.findViewById<TextView>(R.id.vacancy_salary)
    private val imageCompany = itemView.findViewById<ImageView>(R.id.image_company)

    fun bind(vacancy: VacancyDetail) {
        vacancyTitle.text = vacancy.name
        companyTitle.text = vacancy.companyName
        vacancySalary.text = ""// todo - getSalaryString(vacancy.salary)
        Glide.with(itemView)
            .load(vacancy.companyLogo)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_small_2)))
            .into(imageCompany)
    }

    private fun getSalaryString(salary: Salary?): CharSequence {
        return when {
            salary == null -> "Зарплата не указана"
            salary.from != null && salary.to != null && salary.currency != null
            -> "от ${salary.from} до ${salary.to} ${salary.currency}"

            salary.from != null && salary.to != null -> "от ${salary.from} до ${salary.to}"
            salary.from != null && salary.currency != null -> "от ${salary.from} ${salary.currency}"
            salary.to != null && salary.currency != null -> "${salary.to} ${salary.currency}"
            salary.from != null -> "от ${salary.from}"
            salary.to != null -> "${salary.to}"
            else -> "Зарплата не указана"
        }
    }
}
