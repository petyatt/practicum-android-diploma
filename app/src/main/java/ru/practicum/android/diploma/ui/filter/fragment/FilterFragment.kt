package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
    private var currentSalary: Int? = null
    private var checked: Boolean = false

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
            cbFilter.setOnCheckedChangeListener { _, isChecked ->
                checked = isChecked
            }
            salaryVal.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val inputText = salaryVal.text.toString()
                    if (inputText.isNotEmpty()) {
                        currentSalary = inputText.toInt()
                    }
                }
                true
            }
            tvApply.setOnClickListener {
                findNavController().navigateUp()
                saveToSharedPreferences()
            }

            tvReset.setOnClickListener {
                viewModel.clear()
                etPlaceWork.setText("")
                salaryVal.setText("")
            }
            updateButtonsVisibility()
        }
    }

    private fun saveToSharedPreferences() {
        val currentFilter = viewModel.get() ?: Filter()
        currentFilter.showWithoutSalary = checked
        currentFilter.salary = currentSalary
        currentFilter.industry = currentIndustry
        viewModel.save(currentFilter)
    }

    private fun updateUI(filter: Filter) {
        currentIndustry = filter.industry
        with(binding) {
            cbFilter.isChecked = filter.showWithoutSalary ?: false
            etIndustry.setText(currentIndustry?.name)
            val textPlace = "${filter.country?.name}, ${filter.area?.name}"
            etPlaceWork.setText(textPlace)
            salaryVal.setText(filter.salary.toString())
        }
        updateButtonsVisibility()
    }

    private fun updateButtonsVisibility() {
        with(binding) {
            if (currentIndustry != null || currentSalary != null || checked) {
                tvApply.isVisible = true
                tvApply.isEnabled = true
                tvReset.isVisible = true
            } else {
                tvApply.isVisible = false
                tvReset.isVisible = false
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val savedFilter = viewModel.get()
        with(binding) {
            cbFilter.isChecked = savedFilter?.showWithoutSalary ?: false
            if (currentIndustry == null) {
                currentIndustry = savedFilter?.industry
            }
            etIndustry.setText(currentIndustry?.name)
            val textPlace = if (savedFilter?.country == null && savedFilter?.area == null) {
                ""
            } else "${savedFilter.country?.name ?: ""}, ${savedFilter.area?.name ?: ""}"
            etPlaceWork.setText(textPlace)
            if (currentSalary == null) {
                currentSalary = savedFilter?.salary
            }
            val salaryText = if (currentSalary == null) ""
            else currentSalary.toString()

            salaryVal.setText(salaryText)
        }
        updateButtonsVisibility()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
