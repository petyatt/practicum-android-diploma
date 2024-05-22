package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employer(
    val logoUrls: LogoUrls?,
    val name: String?
) : Parcelable
