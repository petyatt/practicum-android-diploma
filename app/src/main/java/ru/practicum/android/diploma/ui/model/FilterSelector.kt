package ru.practicum.android.diploma.ui.model

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText

typealias OnSelectListener = (FilterSelector) -> Unit
typealias OnChangeListener = (FilterSelector, Selectable?) -> Unit

class FilterSelector
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyle) {

    private val closeIcon = ContextCompat.getDrawable(context, ru.practicum.android.diploma.R.drawable.icon_close)
    private val arrowIcon = ContextCompat.getDrawable(context, ru.practicum.android.diploma.R.drawable.arrow_forward)

    var value: Selectable? = null
        set(newValue) {
            if (field != newValue) onChangeListener?.invoke(this, newValue)
            field = newValue
            setText(newValue?.caption)
            setIcon()
        }

    var onSelectListener: OnSelectListener? = null
    var onChangeListener: OnChangeListener? = null

    init {
        setOnTouchListener { v, event ->
            v.performClick()
            if (event.action == MotionEvent.ACTION_UP) {
                if (value == null || event.x < width - totalPaddingRight) {
                    onSelectListener?.invoke(this)
                } else {
                    value = null
                }
                value = null
            }
            true
        }
        setIcon()
    }

    fun setIcon() {
        val icon = if (value == null) arrowIcon else closeIcon
        setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
    }
}
