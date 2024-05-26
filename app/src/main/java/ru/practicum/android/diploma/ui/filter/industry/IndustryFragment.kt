package ru.practicum.android.diploma.ui.filter.industry

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.debounce

class IndustryFragment : Fragment() {
    private val viewModel: IndustryViewModel by viewModel()

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!

    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }
    private var lastSearchText: String = ""

    private var currentIndustry: Industry? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentIndustry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_INDUSTRY, Industry::class.java)
        } else {
            arguments?.getParcelable(ARG_INDUSTRY) as? Industry
        }

        with(binding) {
            buttNav.setOnClickListener { findNavController().navigateUp() }
            viewModel.industriesState.observe(viewLifecycleOwner) {
                when (it) {
                    is ScreenState.Loaded -> show(it.t)
                    else -> showError()
                }
            }
            search.doOnTextChanged { text, _, _, _ -> onSearchDebounce(text.toString()) }
            search.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.text.toString())
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                false
            }
            select.isVisible = currentIndustry != null
            select.setOnClickListener {
                setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(RES_INDUSTRY to (industries.adapter as IndustryAdapter).currentIndustry)
                )
                findNavController().navigateUp()
            }
            search()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun search(text: String = "") {
        lastSearchText = text
        showProgressBar()
        viewModel.getIndustries(text)
    }

    private fun show(data: List<Industry>) {
        if (data.isEmpty()) {
            showEmptyList()
            return
        }
        with(binding) {
            industries.adapter = IndustryAdapter(data) { select.isVisible = true }
            (industries.adapter as IndustryAdapter).currentIndustry = currentIndustry
            emptyList.isVisible = false
            error.isVisible = false
            loading.isVisible = false
            industriesGroup.isVisible = true
        }
    }

    private fun showError() {
        with(binding) {
            emptyList.isVisible = false
            loading.isVisible = false
            industriesGroup.isVisible = false
            error.isVisible = true
        }
    }

    private fun showEmptyList() {
        with(binding) {
            loading.isVisible = false
            industriesGroup.isVisible = false
            error.isVisible = false
            emptyList.isVisible = true
        }
    }

    private fun showProgressBar() {
        with(binding) {
            industriesGroup.isVisible = false
            error.isVisible = false
            emptyList.isVisible = false
            loading.isVisible = true
        }
    }

    companion object {
        private const val ARG_INDUSTRY = "arg_industry"
        private const val REQUEST_KEY = "industry_fragment_request_key"

        private const val RES_INDUSTRY = "res_industry"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        fun createArgument(arg: Industry?): Bundle? {
            val bundle = bundleOf(ARG_INDUSTRY to (arg ?: return null))
            return bundle
        }

        fun createResultListener(fragment: Fragment, onResponse: (Industry) -> Unit) {
            fragment.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                val industry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(RES_INDUSTRY, Industry::class.java)
                } else {
                    bundle.getParcelable(RES_INDUSTRY) as? Industry
                }
                if (industry != null) onResponse.invoke(industry)
                fragment.clearFragmentResultListener(REQUEST_KEY)
            }
        }
    }
}
