package ru.practicum.android.diploma.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneDto(
    val city: String,
    val comment: String?,
    val country: String,
    val formatted: String,
    val number: String
) : Parcelable
