package ru.practicum.android.diploma.ui.filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var currentArea: Area? = null
        set(value) {
            field = value
            binding.apply.isVisible = newFilter() != currentFilter
        }
    private var currentSalary: Int? = null
        set(value) {
            field = value
            binding.apply.isVisible = newFilter() != currentFilter
        }
    private var currentOnlyWithSalary: Boolean = false
        set(value) {
            field = value
            binding.apply.isVisible = newFilter() != currentFilter
        }
    private var currentFilter: Filter? = null
        set(value) {
            field = value
            binding.apply.isVisible = newFilter() != currentFilter
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

            salaryVal.doOnTextChanged { text, _, _, _ ->
                val s = text?.toString()
                currentSalary = if (s.isNullOrEmpty()) null else s.toInt()
            }

            cbFilter.setOnCheckedChangeListener { _, isChecked -> currentOnlyWithSalary = isChecked }

            reset.setOnClickListener { viewModel.clear() }

            apply.setOnClickListener {
                currentFilter = Filter()
                viewModel.save(newFilter())
                findNavController().navigateUp()
            }
            if (currentFilter == null) viewModel.get()
        }
    }

    private fun updateUI(filter: Filter) {
        currentFilter = filter
        with(binding) {
            etPlaceWork.value = filter.area
            etIndustry.value = filter.industry
            salaryVal.setText(filter.salary?.toString() ?: "")
            cbFilter.isChecked = filter.onlyWithSalary
            reset.isVisible = currentFilter?.isEmpty?.not() ?: false
        }
    }

    private fun newFilter() = Filter(
        area = currentArea,
        industry = currentIndustry,
        salary = currentSalary,
        onlyWithSalary = currentOnlyWithSalary
    )

    override fun onResume() {
        super.onResume()

        with(binding) {
            etPlaceWork.value = currentArea
            etIndustry.value = currentIndustry
            salaryVal.setText(currentSalary?.toString() ?: "")
            cbFilter.isChecked = currentOnlyWithSalary
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
