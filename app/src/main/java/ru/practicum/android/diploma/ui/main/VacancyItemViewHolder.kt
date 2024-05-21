package ru.practicum.android.diploma.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyListBinding
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyItemViewHolder(private val binding: ItemVacancyListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.vacancyTitle.text = vacancy.name
        binding.companyTitle.text = vacancy.employer?.name ?: "Работодатель не указан"
        binding.vacancySalary.text = getSalaryString(vacancy.salary)
        Glide.with(binding.root)
            .load(vacancy.employer?.logoUrls?.original)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_small_2)))
            .into(binding.imageCompany)
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
