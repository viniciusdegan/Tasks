package br.edu.ifsp.scl.tasks.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.tasks.databinding.TaskTitleBinding
import br.edu.ifsp.scl.tasks.domain.model.Task

class TaskAdapter(
    private val taskList: List<Task>,
    private val onClickListener: (Task) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Task, TaskAdapter.TaskViewHolder>(differCallback) {

    private lateinit var binding: TaskTitleBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        binding = TaskTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(binding: TaskTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                textTaskTitle.text = task.name
                textTaskName.text = task.writer
                textStatus.text = task.status
                root.setOnClickListener { onClickListener(task) }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

}