package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    delay: Long,
    coroutineScope: CoroutineScope,
    useLast: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var job: Job? = null
    return { p: T ->
        if (useLast) {
            job?.cancel()
        }
        if (job?.isCompleted != false || useLast) {
            job = coroutineScope.launch {
                delay(delay)
                action(p)
            }
        }
    }
}
