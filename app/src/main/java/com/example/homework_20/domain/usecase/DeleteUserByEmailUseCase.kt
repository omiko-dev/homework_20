package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.UserDbRepository
import javax.inject.Inject

class DeleteUserByEmailUseCase @Inject constructor(
    private val userDbRepository: UserDbRepository
) {
    suspend operator fun invoke(email: String) =
        userDbRepository.deleteUserByEmail(email = email.lowercase().trim())
}