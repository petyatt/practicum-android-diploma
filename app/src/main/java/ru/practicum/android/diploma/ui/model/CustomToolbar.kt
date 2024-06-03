package ru.practicum.android.diploma.ui.model

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import ru.practicum.android.diploma.R

typealias BackClickListener = () -> Unit

class CustomToolbar
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = com.google.android.material.R.attr.toolbarStyle
) : Toolbar(context, attrs, defStyle) {

    private val backButton = ImageView(context)
    private val toolbarHeader = TextView(ContextThemeWrapper(context, R.style.text_top_bar))

    private val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
    private val defaultText = customAttrs.getString(R.styleable.CustomToolbar_android_text)

    var text = defaultText
        get() = toolbarHeader.text.toString()
        set(value) {
            toolbarHeader.text = value
            field = value
        }

    var onBackClickListener: BackClickListener? = null
        set(value) {
            backButton.setOnClickListener { value?.invoke() }
            field = value
        }

    init {
        backButton.setImageResource(R.drawable.arrow_back)
        backButton.backgroundTintList = backgroundTintList
        backButton.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(backButton)

        toolbarHeader.text = defaultText
        addView(toolbarHeader)
    }
}
