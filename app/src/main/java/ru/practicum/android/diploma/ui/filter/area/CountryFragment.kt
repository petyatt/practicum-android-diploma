package ru.practicum.android.diploma.ui.filter.area

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.filter.SearchFragment
import ru.practicum.android.diploma.ui.model.ScreenState

class CountryFragment : SearchFragment<Area, AreaViewHolder>(Area::class.java) {

    private val viewModel: CountryViewModel by viewModel()

    override fun getItems(searchText: String) {
        viewModel.getCountries(searchText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(requireContext()) {
            title = getString(R.string.country_title)
            hint = getString(R.string.country_search_hint)
            emptyListMessage = getString(R.string.country_not_found)
        }
        super.onViewCreated(view, savedInstanceState)

        viewModel.countriesState.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loaded -> show(it.t)
                else -> showError()
            }
        }
    }

    private fun show(country: List<Area>) {
        if (country.isEmpty()) {
            showEmptyList()
        } else {
            val adapter = AreaAdapter(country) { setFragmentResult(it) }
            show(adapter)
        }
    }

    companion object {
        fun createResultListener(fragment: Fragment, onResponse: (Area) -> Unit) {
            createResultListener(fragment, Area::class.java, onResponse)
        }
    }
}
