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
     * Inserts a new todo item into the database
     * 
     * @param todoItem The todo item to be inserted
     * @return The ID of the inserted item
     */
    @Insert
    suspend fun insert(todoItem: TodoItem): Long

    /**
     * Retrieves all todo items from the database
     * 
     * @return A list of all todo items
     */
    @Query("SELECT * FROM todo_items")
    suspend fun getAllTodos(): List<TodoItem>

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