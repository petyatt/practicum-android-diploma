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
        with(binding) {
            backButton.setOnClickListener { findNavController().navigateUp() }
            etCountry.onSelectListener = {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) { currentCountry = it }
            }
            etCountry.onChangeListener = { _, v ->
                select.isVisible = etRegion.value != null || v != null
                currentCountry = v as? Area
            }
            etRegion.onSelectListener = {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    createArgument(currentCountry?.id)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) { currentRegion = it }
            }
            etRegion.onChangeListener = { _, v ->
                select.isVisible = etCountry.value != null || v != null
                currentRegion = v as? Area
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
