package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.debounce

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private var vacancyListAdapter = VacancyListAdapter(ArrayList())
    private val binding get() = _binding!!
    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }
    private var lastSearchText: String = ""
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
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                false
            }
            doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    showDefaultState()
                } else {
                    binding.iconSearch.isVisible = false
                    binding.iconClear.isVisible = true
                    onSearchDebounce(text.toString())
                }
            }
        }
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyListAdapter.itemCount
                    if (pos >= itemsCount - 1) {
                        onSearchDebounce(binding.search.toString())
                    }
                }
            }
        })
    }

    private fun search(text: String) {
        if (lastSearchText == text) return
        lastSearchText = text
        viewModel.sendRequest(text)
    }

    private fun render(state: ScreenState<Vacancies>) {
        when (state) {
            is ScreenState.Loading -> showProgressBar()
            is ScreenState.Loaded -> showContent(state.t)
            is ScreenState.NotConnection -> showError(R.drawable.placeholder_no_internet, R.string.bad_connection)
            is ScreenState.ServerError -> showError(R.drawable.placeholder_no_vacancies, R.string.no_vacancies)
        }
    }

    private fun showDefaultState() {
        binding.progressBarCenter.isVisible = false
        binding.placeholderImage.isVisible = true
        binding.placeholderImage.setImageResource(R.drawable.placeholder_search)
        binding.placeholderText.isVisible = false
        binding.search.text?.clear()
        binding.iconSearch.isVisible = true
        binding.iconClear.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressBarCenter.isVisible = true
        binding.placeholderImage.isVisible = false
        binding.placeholderText.isVisible = false
    }

    private fun showError(@DrawableRes image: Int, @StringRes text: Int) {
        with(binding) {
            progressBarCenter.isVisible = false
            placeholderImage.isVisible = true
            placeholderText.isVisible = true

            placeholderImage.setImageResource(image)
            placeholderText.text = getText(text)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(vacancies: Vacancies) {
        binding.progressBarCenter.isVisible = false
        binding.placeholderImage.isVisible = false
        binding.placeholderText.isVisible = false
        vacancyListAdapter.vacancyList.addAll(vacancies.items)
        vacancyListAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
