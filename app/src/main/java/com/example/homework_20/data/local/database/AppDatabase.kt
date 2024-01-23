package com.example.homework_20.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework_20.data.local.dao.UserDao
import com.example.homework_20.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}