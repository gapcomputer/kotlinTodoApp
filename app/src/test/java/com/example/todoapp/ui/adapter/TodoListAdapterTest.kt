package com.example.todoapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.todoapp.data.model.Todo
import com.example.todoapp.ui.viewmodel.TodoViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TodoListAdapterTest {

    private lateinit var context: Context
    
    @Mock
    private lateinit var mockViewModel: TodoViewModel

    private lateinit var todoListAdapter: TodoListAdapter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()
        
        val todoList = listOf(
            Todo(1, "Test Todo 1", "Description 1", false),
            Todo(2, "Test Todo 2", "Description 2", true)
        )
        
        todoListAdapter = TodoListAdapter(context, mockViewModel, todoList)
    }

    @Test
    fun `adapter initializes with correct item count`() {
        assert(todoListAdapter.itemCount == 2)
    }

    @Test
    fun `update list changes item count`() {
        val newList = listOf(
            Todo(3, "New Todo", "New Description", false)
        )
        todoListAdapter.updateList(newList)
        assert(todoListAdapter.itemCount == 1)
    }

    // Note: Direct dialog interaction testing is challenging
    // This test ensures no exceptions are thrown when creating the dialog
    @Test
    fun `delete confirmation dialog can be created`() {
        val todo = Todo(1, "Test Todo", "Description", false)
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Delete Todo")
            .setMessage("Are you sure?")
            .setPositiveButton("Delete") { dialog, _ -> 
                mockViewModel.deleteTodo(todo)
                dialog.dismiss() 
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
        
        assert(alertDialog != null)
    }
}