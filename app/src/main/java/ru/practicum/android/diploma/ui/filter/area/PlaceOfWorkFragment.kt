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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.getParcelable

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentPlaceOfWorkBinding? = null
    private val binding get() = _binding!!

    private var currentCountry: Area? = null
    private var currentRegion: Area? = null

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

        setCountryListeners()
        serRegionListeners()
        with(binding) {
            placeBackButton.setOnClickListener { findNavController().navigateUp() }
            placeApply.setOnClickListener {
                val area = currentRegion?.let { Area(it, currentCountry) } ?: currentCountry
                setFragmentResult(REQUEST_KEY, bundleOf(RES_AREA to area))
                findNavController().navigateUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            placeCountryVal.value = currentCountry
            placeRegionVal.value = currentRegion
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCountryListeners() {
        with(binding.placeCountryVal) {
            onSelectListener = {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) { currentCountry = it }
            }
            onChangeListener = { _, v ->
                val country = v as? Area
                if (country != currentCountry) {
                    currentCountry = country
                    currentRegion = null
                    binding.placeRegionVal.value = null
                }
                binding.placeApply.isVisible = binding.placeRegionVal.value != null || country != null
            }
        }
    }

    private fun serRegionListeners() {
        with(binding.placeRegionVal) {
            onSelectListener = {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    RegionFragment.createArgument(currentCountry)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) {
                    currentCountry = it.parent
                    currentRegion = Area(it)
                }
            }
            onChangeListener = { _, v ->
                val area = v as? Area
                val country = area?.parent
                val region = area?.let { Area(area) }
                if (region != currentRegion) {
                    currentRegion = region
                    if (region != null) {
                        currentCountry = country
                        binding.placeCountryVal.value = country
                    }
                }
                binding.placeApply.isVisible = binding.placeCountryVal.value != null || area != null
            }
        }
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
