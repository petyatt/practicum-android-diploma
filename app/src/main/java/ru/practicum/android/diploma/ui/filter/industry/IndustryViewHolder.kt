package ru.practicum.android.diploma.ui.filter.industry

import android.view.View
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(view: View, val onClick: (Industry) -> Unit) : ViewHolder(view) {

    private val industryView = view.findViewById<AppCompatRadioButton>(R.id.industry)

    var isChecked = industryView.isChecked
        set(value) {
            industryView.isChecked = value
            field = value
        }

    fun bind(model: Industry, checked: Boolean) {
        with(industryView) {
            text = model.name
            isChecked = checked
            setOnClickListener { if (isChecked) onClick.invoke(model) }
        }
    }
}
