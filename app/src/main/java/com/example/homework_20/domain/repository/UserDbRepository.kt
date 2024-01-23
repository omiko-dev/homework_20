package com.example.homework_20.domain.repository

import com.example.homework_20.data.common.Resource
import com.example.homework_20.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface UserDbRepository {
    suspend fun getUserNumber(): Flow<Resource<Int>>
    suspend fun deleteUserByEmail(email: String): Flow<Resource<Unit>>
    suspend fun updateUserByEmail(userModel: UserModel): Flow<Resource<Unit>>
    suspend fun insertUser(userModel: UserModel): Flow<Resource<Unit>>
}