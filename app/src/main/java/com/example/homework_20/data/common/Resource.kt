package com.example.homework_20.data.common

sealed class Resource<out T> {
    class Success<out T>(val success: T, val successMessage: String? = null): Resource<T>()
    class Error(val error: String): Resource<Nothing>()
    data class Loader(val loader: Boolean): Resource<Nothing>()
}