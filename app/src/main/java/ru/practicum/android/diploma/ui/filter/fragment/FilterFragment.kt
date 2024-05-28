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
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.area.PlaceOfWorkFragment
import ru.practicum.android.diploma.ui.filter.industry.IndustryFragment
import ru.practicum.android.diploma.ui.filter.viewmodel.FilterViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by viewModel()

    private var currentIndustry: Industry? = null
    private var currentArea: Area? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filterLiveData.observe(viewLifecycleOwner) { filter ->
            updateUI(filter)
        }

        with(binding) {
            ivFilterBackButton.setOnClickListener { findNavController().navigateUp() }

            etPlaceWork.onSelectListener = {
                findNavController().navigate(
                    R.id.action_filterFragment_to_placeOfWorkFragment,
                    PlaceOfWorkFragment.createArgument(it.value as? Area)
                )
                PlaceOfWorkFragment.createResultListener(this@FilterFragment) { currentArea = it }
            }
            etPlaceWork.onChangeListener = { _, v -> currentArea = v as? Area }

            etIndustry.onSelectListener = {
                findNavController().navigate(
                    R.id.action_filterFragment_to_industryFragment,
                    IndustryFragment.createArgument(it.value as? Industry)
                )
                IndustryFragment.createResultListener(this@FilterFragment) { currentIndustry = it }
            }
            etIndustry.onChangeListener = { _, v -> currentIndustry = v as? Industry }

            tvReset.setOnClickListener { viewModel.clear() }
            updateButtonsVisibility()
        }
    }

    private fun updateUI(filter: Filter) {
        currentIndustry = filter.industry
        binding.etIndustry.setText(currentIndustry?.name)
        updateButtonsVisibility()
    }

    private fun updateButtonsVisibility() {
        val isNotEmpty = isFilterNotEmpty()
        binding.tvApply.isVisible = isNotEmpty
        binding.tvReset.isVisible = isNotEmpty
    }

    private fun isFilterNotEmpty(): Boolean {
        val industryText = binding.etIndustry.text.toString().trim()
        return industryText.isNotEmpty()
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            etPlaceWork.value = currentArea
            etIndustry.value = currentIndustry
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
