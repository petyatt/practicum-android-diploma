package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.util.debounce

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }

    private var lastSearchText: String = ""

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

        with(binding.search) {
            lastSearchText = text.toString()
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.text.toString())
                }
                false
            }
            doOnTextChanged { text, _, _, _ -> onSearchDebounce(text.toString()) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun search(text: String) {
        if (lastSearchText == text) return
        lastSearchText = text
        // todo - здесь вместо лога будет поиск
        Log.d(tag, "Search: " + text)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
