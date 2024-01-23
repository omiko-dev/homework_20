package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.UserDbRepository
import javax.inject.Inject

class GetUserNumberUseCase @Inject constructor(
    private val userDbRepository: UserDbRepository
) {
    suspend operator fun invoke() = userDbRepository.getUserNumber()
}