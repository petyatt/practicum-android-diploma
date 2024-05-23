package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val id: String,
    val name: String,
    val salaryMin: Int?,
    val salaryMax: Int?,
    val currency: String,
    val companyName: String,
    val companyAddress: String,
    val companyLogo: String,
    val experience: String,
    val description: String,
    val employment: String,
    val skills: List<String>,
    val contactName: String,
    val contactEmail: String,
    val contactPhone: String,
    val contactComment: String
) : Parcelable
