package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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
        set(value) {
            field = value
            binding.filterApply.isVisible = newFilter() != currentFilter
            binding.filterReset.isVisible = !newFilter(industry = value).isEmpty
        }
    private var currentArea: Area? = null
        set(value) {
            field = value
            binding.filterApply.isVisible = newFilter() != currentFilter
            binding.filterReset.isVisible = !newFilter(area = value).isEmpty
        }
    private var currentSalary: Int? = null
        set(value) {
            field = value
            binding.filterApply.isVisible = newFilter() != currentFilter
            binding.filterReset.isVisible = !newFilter(salary = value).isEmpty
        }
    private var currentOnlyWithSalary: Boolean = false
        set(value) {
            field = value
            binding.filterApply.isVisible = newFilter() != currentFilter
            binding.filterReset.isVisible = !newFilter(onlyWithSalary = value).isEmpty
        }
    private var currentFilter: Filter? = null
        set(value) {
            field = value
            binding.filterApply.isVisible = newFilter() != currentFilter
            binding.filterReset.isVisible = !newFilter().isEmpty
        }

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

        viewModel.filterLiveData.observe(viewLifecycleOwner) { updateUI(it) }

        with(binding) {
            filterBackButton.setOnClickListener { findNavController().navigateUp() }

            setPlaceWorkListeners()
            setIndustryListeners()
            setSalaryListeners()

            filterReset.setOnClickListener { clearFilter() }
            filterApply.setOnClickListener {
                currentFilter = Filter()
                viewModel.save(newFilter())
                findNavController().navigateUp()
            }
            if (currentFilter == null) viewModel.get()
        }
    }

    private fun setPlaceWorkListeners() {
        with(binding.filterPlaceWork) {
            onSelectListener = {
                findNavController().navigate(
                    R.id.action_filterFragment_to_placeOfWorkFragment,
                    PlaceOfWorkFragment.createArgument(it.value as? Area)
                )
                PlaceOfWorkFragment.createResultListener(this@FilterFragment) { currentArea = it }
            }
            onChangeListener = { _, v -> currentArea = v as? Area }
        }
    }

    private fun setIndustryListeners() {
        with(binding.filterIndustry) {
            onSelectListener = {
                findNavController().navigate(
                    R.id.action_filterFragment_to_industryFragment,
                    IndustryFragment.createArgument(it.value as? Industry)
                )
                IndustryFragment.createResultListener(this@FilterFragment) { currentIndustry = it }
            }
            onChangeListener = { _, v -> currentIndustry = v as? Industry }
        }
    }

    private fun setSalaryListeners() {
        with(binding) {
            val focusColor = ContextCompat.getColor(requireContext(), R.color.Blue)
            val blurColor = filterSalaryCapt.solidColor
            filterSalaryVal.doOnTextChanged { text, _, _, _ ->
                val salary = text?.toString()
                currentSalary = if (salary.isNullOrEmpty()) null else salary.toInt()
                filterSalaryClear.isVisible = salary?.isNotEmpty() ?: false
            }
            filterSalaryClear.setOnClickListener {
                filterSalaryVal.setText("")
            }
            filterSalaryVal.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                filterSalaryCapt.setTextColor(if (hasFocus) focusColor else blurColor)
            }
            filterOnlyWithSalary.setOnCheckedChangeListener { _, isChecked -> currentOnlyWithSalary = isChecked }
        }
    }

    private fun updateUI(filter: Filter) {
        if (currentFilter == filter) return
        with(binding) {
            currentFilter = filter
            filterPlaceWork.value = filter.area
            filterIndustry.value = filter.industry
            filterSalaryVal.setText(filter.salary?.toString() ?: "")
            filterOnlyWithSalary.isChecked = filter.onlyWithSalary
        }
    }

    private fun newFilter(
        area: Area? = currentArea,
        industry: Industry? = currentIndustry,
        salary: Int? = currentSalary,
        onlyWithSalary: Boolean = currentOnlyWithSalary
    ) = Filter(
        area = area,
        industry = industry,
        salary = salary,
        onlyWithSalary = onlyWithSalary
    )

    private fun clearFilter() {
        with(binding) {
            filterSalaryVal.text = null
            filterIndustry.value = null
            filterPlaceWork.value = null
            filterOnlyWithSalary.isChecked = false
        }
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            filterPlaceWork.value = currentArea
            filterIndustry.value = currentIndustry
            filterSalaryVal.setText(currentSalary?.toString() ?: "")
            filterOnlyWithSalary.isChecked = currentOnlyWithSalary
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
