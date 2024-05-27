package ru.practicum.android.diploma.ui.filter.area

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.filter.SearchFragment
import ru.practicum.android.diploma.ui.model.ScreenState

class RegionFragment : SearchFragment<Area, AreaViewHolder>(
    R.string.region_title,
    R.string.region_search_hint,
    R.string.region_not_found,
    Area::class.java
) {

    private val viewModel: RegionViewModel by viewModel()

    private var country: Area? = null

    override fun getItems(searchText: String) {
        viewModel.getRegions(country, searchText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        country = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_REGION, Area::class.java)
        } else {
            arguments?.getParcelable(ARG_REGION) as? Area
        }

        super.onViewCreated(view, savedInstanceState)

        viewModel.regionsState.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loaded -> show(it.t)
                else -> showError()
            }
        }
    }

    private fun show(region: List<Area>) {
        if (region.isEmpty()) {
            showEmptyList()
        } else {
            val adapter = AreaAdapter(region) { setFragmentResult(it) }
            show(adapter)
        }
    }

    companion object {
        private const val ARG_REGION = "arg_region"

        fun createArgument(arg: Area?): Bundle? {
            val bundle = bundleOf(ARG_REGION to (arg ?: return null))
            return bundle
        }

        fun createResultListener(fragment: Fragment, onResponse: (Area) -> Unit) {
            createResultListener(fragment, Area::class.java, onResponse)
        }
    }
}
