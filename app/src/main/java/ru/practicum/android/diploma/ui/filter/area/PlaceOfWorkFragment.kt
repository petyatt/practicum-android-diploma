package ru.practicum.android.diploma.ui.filter.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.filter.area.RegionFragment.Companion.createArgument

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentPlaceOfWorkBinding? = null
    private val binding get() = _binding!!

    private var currentArea: Area? = null

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
        with(binding) {
            backButton.setOnClickListener { findNavController().navigateUp() }
            etCountry.onSelectListener = {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) { currentArea = it }
            }
            etCountry.onChangeListener = { _, v ->
                val area = v as? Area
                currentArea = area
                if (area == null) etRegion.value = null
                select.isVisible = etRegion.value != null || v != null
            }
            etRegion.onSelectListener = {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    createArgument(etCountry.value as? Area)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) { currentArea = it }
            }
            etRegion.onChangeListener = { _, v ->
                val area = v as? Area
                currentArea = area
                select.isVisible = etCountry.value != null || v != null
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showArea(currentArea)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showArea(area: Area?) {
        val country = area?.parent ?: area
        val region = if (area?.parent != null) area else null
        with(binding) {
            etRegion.value = region
            etCountry.value = country
        }
    }
}
