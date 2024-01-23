package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.UserDbRepository
import com.example.homework_20.presentation.mapper.toDomain
import com.example.homework_20.presentation.model.User
import javax.inject.Inject

class UpdateUserByEmailUseCase @Inject constructor(
    private val userDbRepository: UserDbRepository
) {
    suspend operator fun invoke(user: User) =
        userDbRepository.updateUserByEmail(userModel = user.toDomain())
}