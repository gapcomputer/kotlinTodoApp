package com.example.todoapp.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.todoapp.data.database.TodoDatabase
import com.example.todoapp.data.entity.TodoItem
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.IOException

class TodoDaoTest {
    private lateinit var todoDao: TodoDao
    private lateinit var db: TodoDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java)
            .build()
        todoDao = db.todoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun deleteTodoById_deletesSpecificItem() = runBlocking {
        // Arrange: Insert a todo item
        val todoItem = TodoItem(
            id = 1,
            title = "Test Todo",
            description = "Test Description",
            isCompleted = false
        )
        todoDao.insert(todoItem)

        // Act: Delete the todo item
        val deletedCount = todoDao.deleteTodoById(todoItem.id)

        // Assert
        assertEquals(1, deletedCount)
        
        // Verify item is actually deleted
        val remainingItems = todoDao.getAllTodos()
        assertTrue(remainingItems.isEmpty())
    }

    @Test
    fun deleteTodoById_nonExistentItem_returnsZero() = runBlocking {
        // Act: Try to delete a non-existent todo item
        val deletedCount = todoDao.deleteTodoById(999)

        // Assert
        assertEquals(0, deletedCount)
    }
}