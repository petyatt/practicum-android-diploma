package ru.practicum.android.diploma.ui.model

sealed class ScreenState<T> {
    class Loading<T> : ScreenState<T>()
    class Loaded<T>(var t: T) : ScreenState<T>()
    class ServerError<T> : ScreenState<T>()
    class NotConnection<T> : ScreenState<T>()
}
