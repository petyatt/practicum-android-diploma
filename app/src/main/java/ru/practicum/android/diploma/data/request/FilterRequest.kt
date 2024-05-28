package ru.practicum.android.diploma.data.request

class FilterRequest(
    val vacancy: String? = null,
    val page: Int = 0,
    val perPage: Int = 20,
    val searchField: String? = "name",
    val area: String? = null,
    val industry: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) : Request
