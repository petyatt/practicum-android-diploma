package ru.practicum.android.diploma.ui.main

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import ru.practicum.android.diploma.R

class SearchEditText
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle) {

    private val closeIcon = ContextCompat.getDrawable(context, R.drawable.icon_close)
    private val searchIcon = ContextCompat.getDrawable(context, R.drawable.icon_search)

    init {
        onTextChange(this.text)
        doAfterTextChanged { onTextChange(it) }

        setOnTouchListener { v, event ->
            v.performClick()
            if (event.action == MotionEvent.ACTION_UP && event.x >= width - totalPaddingRight) {
                setText("")
                true
            } else {
                false
            }
        }
    }

    private fun onTextChange(view: Editable?) {
        if (view == null) return
        val icon = if (view.isBlank()) {
            searchIcon
        } else {
            closeIcon
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
    }

}
