package ru.practicum.android.diploma.ui.filter.industry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.util.debounce

class IndustryFragment : Fragment() {
    private val viewModel: IndustryViewModel by viewModel()

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!

    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }
    private var lastSearchText: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttNav.setOnClickListener { findNavController().navigateUp() }
            viewModel.industries.observe(viewLifecycleOwner) {
                industries.adapter = IndustryAdapter(it) { select.isVisible = true }
                industries.isVisible = true
                loading.isVisible = false
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
            search()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun search(text: String = "") {
        lastSearchText = text
        binding.industries.isVisible = false
        binding.loading.isVisible = true
        viewModel.getIndustries(text)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}
