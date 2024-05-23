package ru.practicum.android.diploma.ui.favorites.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoritesState
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.main.VacancyListAdapter

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoritesViewModel>()
    private lateinit var adapter: VacancyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VacancyListAdapter(mutableListOf()) { vacancy ->
            if (viewModel.clickDebounce()) {
                val args = Bundle()
                args.putParcelable("vacancy", vacancy)
                findNavController().navigate(R.id.vacancyFragment, args)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.favouriteVacancies.observe(viewLifecycleOwner, ::renderState)

    }

    private fun renderState(state: FavoritesState) {
        when (state) {
            is FavoritesState.Content -> showFavourites(state.data)
            is FavoritesState.Empty -> showEmpty()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showFavourites(favourites: List<Vacancy>) {


        binding.ivPlaceholder.isVisible = false
        binding.recyclerView.isVisible = true

        Log.d("SIZE_FAV", favourites.size.toString())
        adapter.vacancyList.clear()
        adapter.vacancyList.addAll(favourites)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.apply {
            ivPlaceholder.isVisible = true
            recyclerView.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("SIZE", adapter.vacancyList.size.toString())
        viewModel.getVacancies()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPause() {
        adapter.vacancyList.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onPause()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.vacancyList.clear()
    }
}
