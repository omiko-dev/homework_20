package com.example.homework_20.data.local.mapper

import com.example.homework_20.data.local.entity.UserEntity
import com.example.homework_20.domain.model.UserModel

fun UserModel.toData() =
    UserEntity(
        uid = uid,
        firstName = firstName,
        lastName = lastName,
        email = email,
        age = age
    )