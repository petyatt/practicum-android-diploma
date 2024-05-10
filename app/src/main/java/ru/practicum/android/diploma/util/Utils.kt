package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
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

fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
    with(capabilities) {
        return hasTransport(TRANSPORT_CELLULAR) || hasTransport(TRANSPORT_WIFI) || hasTransport(TRANSPORT_ETHERNET)
    }
}
