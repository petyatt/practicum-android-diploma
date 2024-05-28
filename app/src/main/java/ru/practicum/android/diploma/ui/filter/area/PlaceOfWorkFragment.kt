package ru.practicum.android.diploma.ui.filter.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.ui.filter.viewmodel.FilterViewModel
import ru.practicum.android.diploma.util.getParcelable

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentPlaceOfWorkBinding? = null
    private val binding get() = _binding!!

    private var currentCountry: Area? = null
    private var currentRegion: Area? = null
    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentArea = getParcelable(arguments, ARG_AREA, Area::class.java)

        currentCountry = currentCountry ?: currentArea?.parent ?: currentArea
        currentRegion = currentRegion ?: currentArea?.parent?.let { Area(currentArea) }

        with(binding) {
            backButton.setOnClickListener { findNavController().navigateUp() }
            etCountry.onSelectListener = {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) { currentCountry = it }
            }
            etCountry.onChangeListener = { _, v -> countryChange(v as? Area) }
            etRegion.onSelectListener = {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    RegionFragment.createArgument(currentCountry)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) {
                    currentCountry = it.parent
                    currentRegion = Area(it)
                }
            }
            etRegion.onChangeListener = { _, v -> regionChange(v as? Area) }
            select.setOnClickListener {
                val area = currentRegion?.let { Area(it, currentCountry) } ?: currentCountry
                saveToSharedPreferences(currentCountry, currentRegion)
                setFragmentResult(REQUEST_KEY, bundleOf(RES_AREA to area))
                findNavController().navigateUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            etCountry.value = currentCountry
            etRegion.value = currentRegion
        }
    }
    private fun saveToSharedPreferences(country: Area?, area: Area?) {
        val currentFilter = viewModel.get() ?: Filter()
        currentFilter.country = country
        currentFilter.area = area
        viewModel.save(currentFilter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countryChange(country: Area?) {
        if (country != currentCountry) {
            currentCountry = country
            currentRegion = null
            binding.etRegion.value = null
        }
        binding.select.isVisible = binding.etRegion.value != null || country != null
    }

    private fun regionChange(area: Area?) {
        val country = area?.parent
        val region = area?.let { Area(area) }
        if (region != currentRegion) {
            currentRegion = region
            if (region != null) {
                currentCountry = country
                binding.etCountry.value = country
            }
        }
        binding.select.isVisible = binding.etCountry.value != null || area != null
    }

    companion object {
        private const val REQUEST_KEY = "area_fragment_request_key"

        private const val ARG_AREA = "arg_area"
        private const val RES_AREA = "res_area"

        fun createArgument(arg: Area?): Bundle? {
            val bundle = bundleOf(ARG_AREA to (arg ?: return null))
            return bundle
        }

        fun createResultListener(fragment: Fragment, onResponse: (Area) -> Unit) {
            fragment.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                val response = getParcelable(bundle, RES_AREA, Area::class.java)
                response?.apply { onResponse.invoke(response) }
                fragment.clearFragmentResultListener(REQUEST_KEY)
            }
        }

    }
}
