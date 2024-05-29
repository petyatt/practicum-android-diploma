package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.SearchFragment
import ru.practicum.android.diploma.ui.model.ScreenState

class IndustryFragment : SearchFragment<Industry, IndustryViewHolder>(
    R.string.industry_title,
    R.string.industry_search_hint,
    R.string.industry_not_found,
    Industry::class.java
) {

    private val viewModel: IndustryViewModel by viewModel()
    override fun getItems(searchText: String) {
        viewModel.getIndustries(searchText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.industriesState.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loaded -> show(it.t)
                else -> showError()
            }
        }
        onSelect = { setFragmentResult(currentItem) }
    }

    private fun show(industries: List<Industry>) {
        if (industries.isEmpty()) {
            showEmptyList()
        } else {
            val adapter = IndustryAdapter(industries) { currentItem = it }
            adapter.currentIndustry = currentItem
            show(adapter)
        }
    }

    companion object {
        fun createArgument(arg: Industry?): Bundle? = createArgument<Industry>(arg)
        fun createResultListener(fragment: Fragment, onResponse: (Industry) -> Unit) {
            createResultListener(fragment, Industry::class.java, onResponse)
        }
    }
}
