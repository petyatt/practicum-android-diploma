package ru.practicum.android.diploma.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment.Companion.ARG_VACANCY_ID
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.isConnected

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private var vacancyListAdapter: VacancyListAdapter? = null
    private val binding get() = _binding!!

    private val onNeedPage: (Int) -> Unit = { viewModel.getVacancies(lastSearchText, it) }

    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }
    private val onRequestDebounce =
        debounce<String>(REQUEST_DELAY, lifecycleScope, false) { viewModel.getVacancies(it) }
    private val onVacancyClickDebounce = debounce<Vacancy>(
        VACANCY_CLICK_DEBOUNCE_DELAY,
        lifecycleScope,
        false
    ) { vacancy ->
        if (isConnected(requireContext())) {
            findNavController().navigate(
                R.id.action_mainFragment_to_vacancyFragment,
                bundleOf(ARG_VACANCY_ID to vacancy.id)
            )
        } else {
            showPlaceholder(R.drawable.placeholder_no_internet, R.string.bad_connection)
        }
    }
    private var lastSearchText: String = ""
    private var currentFilter = Filter()
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { render(it) }
        showDefaultState()
        setSearchFieldListeners()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = vacancyListAdapter

        binding.ivFilter.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFragment_to_filterFragment
            )
        }
        viewModel.getFilterState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSearchFieldListeners() {
        with(binding.search) {
            lastSearchText = text.toString()
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.text.toString())
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                false
            }
            doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    showDefaultState()
                } else {
                    onSearchDebounce(text.toString())
                }
            }
        }
    }

    private fun search(text: String) {
        lastSearchText = text
        binding.recyclerView.adapter = null
        onRequestDebounce(text)
        if (text.isNotEmpty()) showProgressBar()
    }

    private fun render(state: ScreenState<Vacancies>) {
        when (state) {
            is ScreenState.Loaded -> show(state.t)
            is ScreenState.NotConnection -> showNoInternet()
            is ScreenState.Option<*, *> -> changeFilter(state.value as? Filter ?: Filter())
            else -> showError()
        }
    }

    private fun changeFilter(filter: Filter) {
        if (filter != currentFilter) {
            search(binding.search.text.toString())
        }
        binding.ivFilter.setImageResource(
            if (filter.isEmpty) R.drawable.filter_off else R.drawable.filter_on
        )
        currentFilter = filter
    }

    private fun showDefaultState() {
        with(binding) {
            recyclerView.adapter = null
            showPlaceholder(R.drawable.placeholder_search, R.string.empty_string)
            search.text?.clear()
        }
    }

    private fun showNoInternet() {
        if (binding.recyclerView.adapter == null) {
            showPlaceholder(R.drawable.placeholder_no_internet, R.string.bad_connection)
        } else {
            Toast.makeText(requireContext(), R.string.bad_connection, Toast.LENGTH_LONG).show()
        }
    }

    private fun showError() {
        if (binding.recyclerView.adapter == null) {
            showPlaceholder(R.drawable.placeholder_cat, R.string.no_vacancies)
        } else {
            Toast.makeText(requireContext(), R.string.no_vacancies, Toast.LENGTH_LONG).show()
        }
    }

    private fun showPlaceholder(@DrawableRes image: Int, @StringRes text: Int) {
        with(binding) {
            placeholderImage.setImageResource(image)
            placeholderText.text = getText(text)
            groupVacancies.isVisible = false
            progressBar.isVisible = false
            groupPlaceholder.isVisible = true
        }
    }

    private fun showProgressBar() {
        with(binding) {
            recyclerView.adapter?.run { return }
            groupVacancies.isVisible = false
            groupPlaceholder.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun show(vacancies: Vacancies) {
        with(binding) {
            if (recyclerView.adapter == null) {
                recyclerView.adapter = VacancyListAdapter(vacancies, onVacancyClickDebounce, onNeedPage)

                progressBar.isVisible = false
                placeholderImage.isVisible = false
                placeholderText.isVisible = false
                groupVacancies.isVisible = true
                tvNumberVacancies.text = getStringOfVacancies(vacancies.found)
            } else {
                val adapter = recyclerView.adapter as? VacancyListAdapter
                adapter?.load(vacancies)
            }
        }
    }

    private fun getStringOfVacancies(count: Int): String =
        if (count == 0) resources.getString(R.string.not_find_vacancies) else resources.getQuantityString(
            R.plurals.founded_vacancies,
            count,
            count
        )


    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val VACANCY_CLICK_DEBOUNCE_DELAY = 500L
        private const val REQUEST_DELAY = 1000L
    }
}
