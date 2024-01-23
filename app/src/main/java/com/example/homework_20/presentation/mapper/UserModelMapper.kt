package com.example.homework_20.presentation.mapper

import com.example.homework_20.domain.model.UserModel
import com.example.homework_20.presentation.model.User

fun User.toDomain() =
    UserModel(
        uid = uid,
        firstName = firstName,
        lastName = lastName,
        email = email,
        age = age
    )