package com.example.homework_20.data.local.repository

import com.example.homework_20.data.common.HandleResource
import com.example.homework_20.data.common.Resource
import com.example.homework_20.data.local.dao.UserDao
import com.example.homework_20.data.local.mapper.toData
import com.example.homework_20.domain.model.UserModel
import com.example.homework_20.domain.repository.UserDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDbRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val handleResource: HandleResource
) : UserDbRepository {
    override suspend fun getUserNumber(): Flow<Resource<Int>> =
        handleResource.handleResource(
            call = {
                userDao.getAll().count()
            }
        )

    override suspend fun deleteUserByEmail(email: String): Flow<Resource<Unit>> =
        handleResource.handleResource(
            call = {
                val users = userDao.getAll()
                if (users.any { it.email == email }) {
                    userDao.deleteByEmail(email = email)
                } else
                    null
            },
            successMessage = DeleteUserByEmailMessage.SUCCESS.message,
            errorMessage = DeleteUserByEmailMessage.ERROR.message
        )

    override suspend fun updateUserByEmail(userModel: UserModel): Flow<Resource<Unit>> =
        handleResource.handleResource(
            call = {
                userDao.updateByEmail(
                    email = userModel.email,
                    firstName = userModel.firstName,
                    lastName = userModel.lastName,
                    age = userModel.age
                )
            },
            successMessage = UpdateUserByEmailMessage.SUCCESS.message,
            errorMessage = UpdateUserByEmailMessage.ERROR.message
        )


    override suspend fun insertUser(userModel: UserModel): Flow<Resource<Unit>> {
        return handleResource.handleResource(
            call = {
                val users = userDao.getAll()
                if (users.none { it.email == userModel.email }) {
                    userDao.insert(userModel.toData())
                } else {
                    null
                }
            },
            successMessage = InsertUserMessage.SUCCESS.message,
            errorMessage = InsertUserMessage.ERROR.message
        )
    }
}

enum class DeleteUserByEmailMessage(val message: String) {
    SUCCESS(message = "User deleted successfully"),
    ERROR(message = "User does not exits")
}

enum class InsertUserMessage(val message: String) {
    SUCCESS(message = "User added successfully"),
    ERROR(message = "User already exists")
}

enum class UpdateUserByEmailMessage(val message: String) {
    SUCCESS(message = "User update successfully"),
    ERROR(message = "User does not exits")
}