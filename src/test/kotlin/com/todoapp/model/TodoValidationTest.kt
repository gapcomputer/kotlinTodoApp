package com.todoapp.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TodoValidationTest {

    @Test
    fun `valid todo item should pass validation`() {
        val validTodo = Todo(
            title = "Complete project",
            description = "Finish the todo app implementation",
            priority = Todo.Priority.HIGH
        )

        // Should not throw any exception
        assertNotNull(Todo.Companion.validate(validTodo))
    }

    @Test
    fun `empty title should throw validation exception`() {
        val invalidTodo = Todo(
            title = "",
            description = "Some description"
        )

        val exception = assertThrows<Todo.Companion.ValidationException> {
            Todo.Companion.validate(invalidTodo)
        }

        assertEquals("Title cannot be empty", exception.message)
    }

    @Test
    fun `title exceeding max length should throw validation exception`() {
        val longTitle = "a".repeat(101)
        val invalidTodo = Todo(title = longTitle)

        val exception = assertThrows<Todo.Companion.ValidationException> {
            Todo.Companion.validate(invalidTodo)
        }

        assertEquals("Title cannot exceed 100 characters", exception.message)
    }

    @Test
    fun `description exceeding max length should throw validation exception`() {
        val longDescription = "a".repeat(501)
        val invalidTodo = Todo(
            title = "Valid Title",
            description = longDescription
        )

        val exception = assertThrows<Todo.Companion.ValidationException> {
            Todo.Companion.validate(invalidTodo)
        }

        assertEquals("Description cannot exceed 500 characters", exception.message)
    }

    @Test
    fun `title with invalid characters should throw validation exception`() {
        val invalidTodo = Todo(
            title = "Invalid Title @#$%"
        )

        val exception = assertThrows<Todo.Companion.ValidationException> {
            Todo.Companion.validate(invalidTodo)
        }

        assertEquals("Title contains invalid characters", exception.message)
    }
}