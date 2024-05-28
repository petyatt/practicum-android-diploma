package ru.practicum.android.diploma.data.request

class MainRequest(val vacancy: String, val page: Int, val filter: Map<String, String>) : Request
