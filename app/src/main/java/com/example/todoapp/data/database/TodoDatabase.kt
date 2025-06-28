package com.example.todoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.dao.TodoDao
import com.example.todoapp.data.entity.TodoItem

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        // Singleton implementation can be added here in future
    }
}