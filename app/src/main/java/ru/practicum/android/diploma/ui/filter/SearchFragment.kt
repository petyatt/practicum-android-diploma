package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.util.debounce

abstract class SearchFragment<T, H : ViewHolder>(
    @StringRes val title: Int,
    @StringRes val hint: Int,
    @StringRes val emptyListMessage: Int,
    val clazz: Class<T>
) : Fragment() {

    protected var onSelect: () -> Unit = {}
    protected var currentItem: T? = null
        set(value) {
            binding.select.isVisible = value != null
            field = value
        }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) { search(it) }
    private var lastSearchText: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_SEARCH, clazz)
        } else {
            clazz.cast(arguments?.getParcelable(ARG_SEARCH))
        }

        with(binding) {
            textTopBar.setText(title)
            search.setHint(hint)
            notFoundMessage.setText(emptyListMessage)

            buttNav.setOnClickListener { findNavController().navigateUp() }
            search.doOnTextChanged { text, _, _, _ -> onSearchDebounce(text.toString()) }
            search.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.text.toString())
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                false
            }
            select.setOnClickListener { onSelect.invoke() }
            search()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getItems(searchText: String)

    protected fun setFragmentResult(result: T?) {
        setFragmentResult(REQUEST_KEY, bundleOf(RES_SEARCH to result))
        findNavController().navigateUp()
    }

    protected fun show(adapter: Adapter<H>) {
        with(binding) {
            items.adapter = adapter
            emptyList.isVisible = false
            error.isVisible = false
            loading.isVisible = false
            itemsGroup.isVisible = true
        }
    }

    protected fun showError() {
        with(binding) {
            emptyList.isVisible = false
            loading.isVisible = false
            itemsGroup.isVisible = false
            error.isVisible = true
        }
    }

    protected fun showEmptyList() {
        with(binding) {
            loading.isVisible = false
            itemsGroup.isVisible = false
            error.isVisible = false
            emptyList.isVisible = true
        }
    }

    private fun showProgressBar() {
        with(binding) {
            itemsGroup.isVisible = false
            error.isVisible = false
            emptyList.isVisible = false
            loading.isVisible = true
        }
    }

    private fun search(text: String = "") {
        lastSearchText = text
        showProgressBar()
        getItems(text)
    }

    companion object {
        private const val ARG_SEARCH = "arg_search"
        private const val REQUEST_KEY = "search_fragment_request_key"

        private const val RES_SEARCH = "res_search"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        fun <T : Parcelable> createArgument(arg: T?): Bundle? {
            val bundle = bundleOf(ARG_SEARCH to (arg ?: return null))
            return bundle
        }

        fun <T : Parcelable> createResultListener(fragment: Fragment, clazz: Class<T>, onResponse: (T) -> Unit) {
            fragment.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                val response = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(RES_SEARCH, clazz)
                } else {
                    clazz.cast(bundle.getParcelable(RES_SEARCH))
                }
                if (response != null) onResponse.invoke(response)
                fragment.clearFragmentResultListener(REQUEST_KEY)
            }
        }
    }
}
