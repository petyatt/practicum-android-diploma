package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.ui.filter.area.CountryFragment
import ru.practicum.android.diploma.ui.filter.area.RegionFragment
import ru.practicum.android.diploma.ui.filter.area.RegionFragment.Companion.createArgument
import ru.practicum.android.diploma.ui.filter.viewmodel.PlaceOfWorkViewModel

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentPlaceOfWorkBinding? = null
    private val binding get() = _binding!!

    private var currentCountry: Area? = null
    private var currentRegion: Area? = null
    private val viewModel: PlaceOfWorkViewModel by viewModel()

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
        viewModel.filterLiveData.observe(viewLifecycleOwner) { filter ->
            updateUI(filter)
        }
        with(binding) {
            backButton.setOnClickListener { findNavController().navigateUp() }
            etCountry.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
                CountryFragment.createResultListener(this@PlaceOfWorkFragment) {
                    updateButton()
                    currentCountry = it
                }
            }
            etRegion.setOnClickListener {
                findNavController().navigate(
                    R.id.action_placeOfWorkFragment_to_regionFragment,
                    createArgument(currentCountry?.id)
                )
                RegionFragment.createResultListener(this@PlaceOfWorkFragment) {
                    currentRegion = it
                    updateButton()
                }

            }
            selectionButton.setOnClickListener {
                saveToSharedPreferences(currentCountry, currentRegion)
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUI(filter: Filter?) {
        with(binding) {
            etCountry.setText(filter?.country?.name)
            etRegion.setText(filter?.area?.name)
        }
    }

    private fun updateButton() {
        if (currentCountry != null && currentRegion != null) {
            binding.selectionButton.isVisible = true
            binding.selectionButton.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            etCountry.setText(currentCountry?.name)
            etRegion.setText(currentRegion?.name)
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
}
