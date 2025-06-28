package com.todoapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todoapp.data.Todo
import com.todoapp.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TodoViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockRepository: TodoRepository

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)
        
        // Set the main dispatcher for coroutines to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Prepare the ViewModel with mock repository
        viewModel = TodoViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `deleteTodo calls repository delete method`() = runTest {
        // Arrange
        val todoToDelete = Todo(
            id = 1, 
            title = "Test Todo", 
            description = "Test Description", 
            isCompleted = false
        )

        // Setup mock repository to return an initial list and allow deletion
        whenever(mockRepository.getAllTodos()).thenReturn(flowOf(listOf(todoToDelete)))

        // Act
        viewModel.deleteTodo(todoToDelete)

        // Assert
        // Verify that the repository's delete method was called with the correct todo
        verify(mockRepository).deleteTodo(todoToDelete)
    }
}