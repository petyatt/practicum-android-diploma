package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.industry.IndustryFragment.Companion.createArgument
import ru.practicum.android.diploma.ui.filter.industry.IndustryFragment.Companion.createResultListener

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private var currentIndustry: Industry? = null

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
        with(binding) {
            ivFilterBackButton.setOnClickListener { findNavController().navigateUp() }
            etIndustry.setOnClickListener {
                findNavController().navigate(
                    R.id.action_filterFragment_to_industryFragment,
                    createArgument(currentIndustry)
                )
                createResultListener(this@FilterFragment) { currentIndustry = it }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.etIndustry.setText(currentIndustry?.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
