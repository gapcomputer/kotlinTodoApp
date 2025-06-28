package com.example.todoapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.Todo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.times

@RunWith(MockitoJUnitRunner::class)
class TodoAdapterTest {

    private lateinit var todoAdapter: TodoAdapter

    @Mock
    private lateinit var onDeleteClickMock: (Todo) -> Unit

    @Mock
    private lateinit var onCompleteToggleMock: (Todo) -> Unit

    @Before
    fun setup() {
        todoAdapter = TodoAdapter(onDeleteClickMock, onCompleteToggleMock)
    }

    @Test
    fun testAdapterItemCount() {
        val todos = listOf(
            Todo(1, "Task 1", false),
            Todo(2, "Task 2", true)
        )
        todoAdapter.submitList(todos)
        
        assertEquals(2, todoAdapter.itemCount)
    }

    @Test
    fun testDeleteButtonClick() {
        val todo = Todo(1, "Test Todo", false)
        todoAdapter.submitList(listOf(todo))

        val viewHolder = todoAdapter.createViewHolder(
            RecyclerView(todoAdapter.toString()),
            0
        )

        // Simulate delete button click
        viewHolder.itemView.findViewById<View>(
            todoAdapter.toString().indexOf("btnDeleteTodo")
        ).performClick()

        // Verify delete click handler was called
        verify(onDeleteClickMock, times(1))(todo)
    }

    @Test
    fun testCompletionToggle() {
        val todo = Todo(1, "Test Todo", false)
        todoAdapter.submitList(listOf(todo))

        val viewHolder = todoAdapter.createViewHolder(
            RecyclerView(todoAdapter.toString()),
            0
        )

        // Simulate completion toggle
        viewHolder.itemView.findViewById<View>(
            todoAdapter.toString().indexOf("cbTodoComplete")
        ).performClick()

        // Verify completion toggle handler was called
        verify(onCompleteToggleMock, times(1))(todo.copy(isCompleted = true))
    }
}