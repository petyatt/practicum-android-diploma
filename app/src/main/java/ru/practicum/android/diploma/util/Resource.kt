package ru.practicum.android.diploma.util

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class NotConnection<T> : Resource<T>()
    class Failed<T> : Resource<T>()
}
