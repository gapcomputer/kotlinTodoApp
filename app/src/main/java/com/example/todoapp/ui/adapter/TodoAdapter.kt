package com.example.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Todo
import com.example.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onDeleteClick: (Todo) -> Unit,
    private val onCompleteToggle: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return TodoViewHolder(binding, onDeleteClick, onCompleteToggle)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onDeleteClick: (Todo) -> Unit,
        private val onCompleteToggle: (Todo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(todo: Todo) {
            binding.apply {
                // Set todo title
                tvTodoTitle.text = todo.title
                
                // Set completion status
                cbTodoComplete.isChecked = todo.isCompleted
                cbTodoComplete.setOnCheckedChangeListener { _, isChecked ->
                    onCompleteToggle(todo.copy(isCompleted = isChecked))
                }
                
                // Set up delete button
                btnDeleteTodo.setOnClickListener {
                    onDeleteClick(todo)
                }
            }
        }
    }

    // Diff callback for efficient list updates
    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}