package com.example.homework_20.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.homework_20.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<UserEntity>

    @Query("UPDATE user SET first_name = :firstName, last_name = :lastName, age = :age WHERE email LIKE :email")
    fun updateByEmail(email: String, firstName: String, lastName: String, age: Int)

    @Insert
    fun insert(user: UserEntity)

    @Query("DELETE FROM user WHERE email LIKE :email")
    fun deleteByEmail(email: String)
}