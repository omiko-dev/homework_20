package com.example.homework_20.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HandleResource {
    fun <T : Any> handleResource(
        call: () -> T?,
        successMessage: String = CommonResourceMessage.SUCCESS.message,
        errorMessage: String = CommonResourceMessage.ERROR.message
    ): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Loader(loader = true))
                val result = call()
                if(result != null){
                    emit(Resource.Success(success = result, successMessage = successMessage))
                }else{
                    emit(Resource.Error(error = errorMessage))
                }
            } catch (e: Exception) {
                emit(Resource.Error(error = e.message ?: ""))
            } finally {
                emit(Resource.Loader(loader = false))
            }
        }
    }
}

enum class CommonResourceMessage(val message: String){
    SUCCESS(message = "Success Operation"),
    ERROR(message = "Error")
}