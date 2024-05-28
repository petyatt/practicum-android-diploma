package ru.practicum.android.diploma.ui.vacancy

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.model.CURRENCY_SYMBOLS
import ru.practicum.android.diploma.ui.model.ScreenState
import java.util.Locale

class VacancyFragment : Fragment() {

    private val viewModel: VacancyViewModel by viewModel()

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var vacancy: Vacancy? = null

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
            viewModel.vacancyState.observe(viewLifecycleOwner) {
                when (it) {
                    is ScreenState.Loading -> {
                        data.isVisible = false
                        error.isVisible = false
                        loading.isVisible = true
                    }

                    is ScreenState.Loaded -> {
                        vacancy = it.t
                        showVacancy()
                        viewModel.getIsFavorite(it.t.id)
                    }

                    is ScreenState.Option<*, *> -> renderFavorite(it.value as? Boolean ?: false)
                    else -> {
                        data.isVisible = false
                        loading.isVisible = false
                        error.isVisible = true
                    }
                }
            }

            buttFav.setOnClickListener { if (vacancy != null) viewModel.changeIsFavorite(vacancy!!) }
        }

        val id = requireArguments().getString(ARG_VACANCY_ID, "")
        if (id.isBlank()) {
            findNavController().navigateUp()
            return
        }
        viewModel.getVacancyState(id)

        binding.buttShare.setOnClickListener {
            if (vacancy != null) viewModel.shareVacation(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showVacancy() {
        if (vacancy == null) return

        with(vacancy!!) {
            binding.name.text = name
            binding.salaryMin.isVisible = salaryMin != null
            binding.salaryMinVal.text = String.format(Locale.forLanguageTag("ru"), "%,d", salaryMin)
            binding.salaryMax.isVisible = salaryMax != null
            binding.salaryMaxVal.text = String.format(Locale.forLanguageTag("ru"), "%,d", salaryMax)
            binding.salaryCurrency.text = CURRENCY_SYMBOLS[currency] ?: ""
            binding.salaryCurrency.isVisible = salaryMin != null || salaryMax != null
            binding.noSalary.isVisible = salaryMin == null && salaryMax == null

            loadLogo(companyLogo)
            binding.companyName.text = companyName
            binding.companyAddress.text = companyAddress

            binding.experience.isVisible = experience.isNotBlank()
            binding.experienceVal.text = experience

            binding.employment.isVisible = employment.isNotBlank()
            binding.employment.text = employment

            binding.description.isVisible = description.isNotBlank()
            binding.descriptionVal.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)

            binding.skills.isVisible = skills.isNotEmpty()
            binding.skillsVal.text = skills.joinToString("\n") { "• $it" }

            binding.contactsName.isVisible = contactName.isNotBlank()
            binding.contactsNameVal.text = contactName
            binding.contactsEmail.isVisible = contactEmail.isNotBlank()
            binding.contactsEmailVal.text = contactEmail
            binding.contactsPhone.isVisible = contactPhone.isNotBlank()
            binding.contactsPhoneVal.text = contactPhone
            binding.contactsComment.isVisible = contactComment.isNotBlank()
            binding.contactsCommentVal.text = contactComment
            binding.contacts.isVisible = contactName.isNotBlank() || contactEmail.isNotBlank() ||
                contactPhone.isNotBlank() ||
                contactComment.isNotBlank()
        }
        binding.loading.isVisible = false
        binding.data.isVisible = true
        binding.error.isVisible = false
    }

    private fun renderFavorite(favorite: Boolean) {
        if (favorite) {
            binding.buttFav.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.favorites_on_))
        } else {
            binding.buttFav.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.favorites_off))
        }
    }

    private fun loadLogo(uri: String) {
        if (uri.isNotBlank()) {
            Glide.with(this@VacancyFragment)
                .load(Uri.parse(uri))
                .placeholder(R.drawable.placeholder)
                .into(binding.companyLogo)
        } else {
            val placeholder = ContextCompat.getDrawable(requireContext(), R.drawable.placeholder)
            binding.companyLogo.setImageDrawable(placeholder)
        }
    }

    companion object {
        const val ARG_VACANCY_ID = "vacancy_id"
    }
}
