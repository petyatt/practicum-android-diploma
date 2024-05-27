package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.filter.area.CountryFragment
import ru.practicum.android.diploma.ui.filter.area.RegionFragment
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
            etCountry.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) { currentCountry = it }
            }
            etRegion.setOnClickListener {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    createArgument(currentCountry?.id)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) { currentRegion = it }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            etCountry.setText(currentCountry?.name)
            etRegion.setText(currentRegion?.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
