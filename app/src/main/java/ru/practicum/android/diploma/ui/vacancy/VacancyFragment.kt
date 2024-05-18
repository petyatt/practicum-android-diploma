package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.ui.model.ScreenState

class VacancyFragment : Fragment() {

    private val viewModel: VacancyViewModel by viewModel()

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttNav.setOnClickListener { findNavController().navigateUp() }
            buttFav.setOnClickListener { TODO("Обработка добавления в избранное") }
            buttShare.setOnClickListener { TODO("Обработка поделиться") }
        }

        viewModel.vacancyState.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> {
                    TODO("Отображение индикатора загрузки")
                }

                is ScreenState.Loaded -> {
                    TODO("Заполнение полей вакансии")
                }

                else -> {
                    TODO("Обработка ошибки")
                }
            }
        }

        val id = requireArguments().getString(ARG_VACANCY_ID, "")
        viewModel.getVacancyState(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_VACANCY_ID = "vacancy_id"
    }
}
