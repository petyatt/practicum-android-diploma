package ru.practicum.android.diploma.ui.favorites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.main.VacancyListAdapter
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment.Companion.ARG_VACANCY_ID
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoritesViewModel>()

    private val onVacancyClickDebounce = debounce<Vacancy>(VACANCY_CLICK_DEBOUNCE_DELAY, lifecycleScope, false) {
        findNavController().navigate(
            R.id.action_favoritesFragment_to_vacancyFragment,
            bundleOf(ARG_VACANCY_ID to it.id)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.favouriteVacancies.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(favourites: List<Vacancy>) {
        with(binding) {
            progressBar.isVisible = false
            if (favourites.isNotEmpty()) {
                placeholder.isVisible = false
                recyclerView.isVisible = true
                recyclerView.adapter = VacancyListAdapter(Vacancies(items = favourites), onVacancyClickDebounce)
            } else {
                placeholder.isVisible = true
                recyclerView.isVisible = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVacancies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding) {
            progressBar.isVisible = true
            placeholder.isVisible = false
            recyclerView.isVisible = false
        }
        _binding = null
    }

    companion object {
        private const val VACANCY_CLICK_DEBOUNCE_DELAY = 500L
    }
}
