package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Vacancy(
    val id: String,
    val name: String,
    val area: Area,
    val employer: Employer?,
    val employment: Employment?,
    val salary: Salary?
) : Parcelable, Serializable
