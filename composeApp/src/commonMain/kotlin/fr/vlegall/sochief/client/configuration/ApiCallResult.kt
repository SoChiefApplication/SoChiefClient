package fr.vlegall.sochief.client.configuration

sealed class ApiCallResult<out T> {
    data class Ok<T>(val value: T) : ApiCallResult<T>()
    data class NetworkError(val message: String) : ApiCallResult<Nothing>()
    data class NeedsReconfigure(val message: String) : ApiCallResult<Nothing>()
    data class HttpError(val code: Int, val message: String) : ApiCallResult<Nothing>()
    data class UnexpectedError(val message: String) : ApiCallResult<Nothing>()
}