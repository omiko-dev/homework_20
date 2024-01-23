package com.example.homework_20.domain.usecase

import com.example.homework_20.data.common.Resource
import com.example.homework_20.domain.repository.UserDbRepository
import com.example.homework_20.presentation.mapper.toDomain
import com.example.homework_20.presentation.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val userDbRepository: UserDbRepository
) {
    suspend operator fun invoke(user: User): Flow<Resource<Unit>> {
        val newUser = user.copy(
            uid = user.uid,
            firstName = user.firstName.lowercase().trim(),
            lastName = user.lastName.lowercase().trim(),
            email = user.email.lowercase().trim(),
            age = user.age,
        )
        return userDbRepository.insertUser(userModel = newUser.toDomain())
    }
}