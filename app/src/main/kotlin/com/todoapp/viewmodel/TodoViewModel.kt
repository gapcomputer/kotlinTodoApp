package com.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.data.Todo
import com.todoapp.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    // Private mutable state to hold the list of todos
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    
    // Expose an immutable state flow to the UI
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    // Initialize the view model by loading todos
    init {
        loadTodos()
    }

    // Load todos from the repository
    private fun loadTodos() {
        viewModelScope.launch {
            repository.getAllTodos().collect { todoList ->
                _todos.value = todoList
            }
        }
    }

    // Method to delete a todo item
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                // Delete the todo from the repository
                repository.deleteTodo(todo)
                // No need to manually update the list as the flow will handle it
            } catch (e: Exception) {
                // Handle potential deletion errors 
                // You might want to add error handling mechanism like logging or showing an error message
                println("Error deleting todo: ${e.message}")
            }
        }
    }
}