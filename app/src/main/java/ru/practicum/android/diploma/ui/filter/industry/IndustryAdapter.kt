package ru.practicum.android.diploma.ui.filter.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(private val industries: List<Industry>, private val onChange: (Industry) -> Unit) :
    Adapter<IndustryViewHolder>() {

    private val industriesMap = mutableMapOf<Industry, IndustryViewHolder>()
    var currentIndustry: Industry? = null
        set(value) {
            val holder = industriesMap[field]
            if (value != field) {
                field = value
                holder?.isChecked = false
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_industry, parent, false)
        return IndustryViewHolder(view) {
            currentIndustry = it
            onChange.invoke(it)
        }
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industries[position]
        industriesMap[industry] = holder
        holder.bind(industry, industry == currentIndustry)
    }

    override fun getItemCount() = industries.size
}
