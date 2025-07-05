package com.example.todoapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Todo
import com.example.todoapp.databinding.ItemTodoBinding
import com.example.todoapp.ui.viewmodel.TodoViewModel

/**
 * RecyclerView adapter for displaying and managing Todo items
 * 
 * @property context Android context for showing dialogs
 * @property viewModel ViewModel for handling Todo item operations
 * @property todoList List of Todo items to display
 */
class TodoListAdapter(
    private val context: Context,
    private val viewModel: TodoViewModel,
    private var todoList: List<Todo>
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    /**
     * Shows a confirmation dialog before deleting a Todo item
     * 
     * @param todo The Todo item to be deleted
     */
    private fun showDeleteConfirmationDialog(todo: Todo) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete_todo_title)
            .setMessage(R.string.delete_todo_message)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                // Confirm deletion
                viewModel.deleteTodo(todo)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                // Cancel deletion
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int = todoList.size

    inner class TodoViewHolder(private val binding: ItemTodoBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(todo: Todo) {
            binding.apply {
                // Set todo details
                todoTitle.text = todo.title
                todoDescription.text = todo.description

                // Delete button click listener with confirmation dialog
                deleteButton.setOnClickListener {
                    showDeleteConfirmationDialog(todo)
                }
            }
        }
    }

    /**
     * Update the list of Todo items and notify the adapter
     * 
     * @param newList New list of Todo items
     */
    fun updateList(newList: List<Todo>) {
        todoList = newList
        notifyDataSetChanged()
    }
}