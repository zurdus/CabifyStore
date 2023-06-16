package com.zurdus.cabifystore.common.response

/**
 * The result of an operation. Can be a [Success], in which case it will return some data,
 * or a [Failure], returning an [Error] with the cause.
 */
sealed interface Response<T> {

    data class Success<T>(val data: T) : Response<T>

    data class Failure<T>(val error: ResponseError) : Response<T>
}

sealed interface ResponseError {

    object Network : ResponseError

    object Unknown : ResponseError
}


inline fun <reified T> Response<T>.doOnSuccess(onSuccess: (data: T) -> Unit): Response<T> = apply {
    if (this is Response.Success) onSuccess(data)
}

inline fun <reified T> Response<T>.doOnFailure(onFailure: (error: ResponseError) -> Unit): Response<T> = apply {
    if (this is Response.Failure) onFailure(error)
}
