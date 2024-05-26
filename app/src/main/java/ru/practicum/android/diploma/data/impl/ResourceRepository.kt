package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.response.Response
import ru.practicum.android.diploma.util.Resource

abstract class ResourceRepository {
    protected fun <T> getResource(response: Response, data: T?): Resource<T> = when (response.resultCode) {
        ResponseCode.SUCCESS -> Resource.Success(data!!)
        ResponseCode.NOT_CONNECTION -> Resource.NotConnection()
        else -> Resource.Failed()
    }
}
