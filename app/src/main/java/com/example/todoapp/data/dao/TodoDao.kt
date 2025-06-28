package com.example.todoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.entity.TodoItem

@Dao
interface TodoDao {
    /**
     * Deletes a specific todo item from the database by its ID
     * 
     * @param id The unique identifier of the todo item to be deleted
     * @return Number of rows affected (should be 1 if deletion is successful)
     */
    @Query("DELETE FROM todo_items WHERE id = :id")
    suspend fun deleteTodoById(id: Long): Int

    /**
     * Alternative deletion method using the entire TodoItem object
     * 
     * @param todoItem The todo item to be deleted
     */
    @Delete
    suspend fun deleteTodo(todoItem: TodoItem)
}