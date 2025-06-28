package com.todoapp.model

/**
 * Represents a Todo item with validation logic
 * 
 * @property title The title of the todo item
 * @property description Optional description of the todo item
 * @property priority Priority level of the todo item
 * @property isCompleted Completion status of the todo item
 */
data class Todo(
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.LOW,
    val isCompleted: Boolean = false
) {
    // Validation enum for priority levels
    enum class Priority {
        LOW, MEDIUM, HIGH
    }

    // Companion object for centralized validation
    companion object {
        // Validation constants
        private const val MAX_TITLE_LENGTH = 100
        private const val MAX_DESCRIPTION_LENGTH = 500

        /**
         * Validates a todo item's properties
         * 
         * @throws ValidationException if validation fails
         */
        fun validate(todo: Todo) {
            validateTitle(todo.title)
            todo.description?.let { validateDescription(it) }
        }

        /**
         * Validates the title of a todo item
         * 
         * @throws ValidationException if title is invalid
         */
        private fun validateTitle(title: String) {
            when {
                title.isBlank() -> 
                    throw ValidationException("Title cannot be empty")
                title.length > MAX_TITLE_LENGTH -> 
                    throw ValidationException("Title cannot exceed $MAX_TITLE_LENGTH characters")
                !title.matches(Regex("^[a-zA-Z0-9\\s.,!?-]+$")) -> 
                    throw ValidationException("Title contains invalid characters")
            }
        }

        /**
         * Validates the description of a todo item
         * 
         * @throws ValidationException if description is invalid
         */
        private fun validateDescription(description: String) {
            if (description.length > MAX_DESCRIPTION_LENGTH) {
                throw ValidationException("Description cannot exceed $MAX_DESCRIPTION_LENGTH characters")
            }
        }
    }

    // Custom exception for validation errors
    class ValidationException(message: String) : Exception(message)
}