package ru.practicum.android.diploma.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
            .addHeader("HH-User-Agent", "DW_YP (petya.07@yandex.ru)")
            .build()
        return chain.proceed(request)
    }
}
