package com.example.homework_20.presentation.screen.event

import com.example.homework_20.presentation.model.User

sealed class MainEvent {
    data class DeleteUserByEmail(val email: String): MainEvent()
    data class InsertUser(val user: User): MainEvent()
    data class UpdateUserByEmail(val user: User): MainEvent()
    data object GetUserNumber: MainEvent()
}