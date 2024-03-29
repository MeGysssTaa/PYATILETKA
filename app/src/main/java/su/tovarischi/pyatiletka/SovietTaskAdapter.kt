package su.tovarischi.pyatiletka

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class SovietTaskAdapter(
    private val context: Context,
    private val interactionListener: InteractionListener,
) : RecyclerView.Adapter<SovietTaskAdapter.TaskViewHolder>() {
    private companion object {
        val DISPLAY_DATETIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")
    }

    private val tasks = mutableListOf<SovietTask>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<SovietTask>) {
        tasks.clear()
        tasks += newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, position)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val taskCreatedDateTimeTextView: TextView = itemView.findViewById(R.id.taskCreatedDateTimeTextView)
        private val taskDetailsTextView: TextView = itemView.findViewById(R.id.taskDetailsTextView)
        private val taskIsCompletedCheckBox: CheckBox = itemView.findViewById(R.id.taskIsCompletedCheckBox)
        private val deleteTaskButton: ImageButton = itemView.findViewById(R.id.deleteTaskButton)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(task: SovietTask, position: Int) {
            updateDisplay(task)

            taskIsCompletedCheckBox.setOnClickListener {
                if (interactionListener.setIsCompleted(task, taskIsCompletedCheckBox.isChecked)) {
                    // Set successful.
                    updateDisplay(task)
                    // Re-sort.
                    tasks.sortByDescending { it.created }
                    tasks.sortBy { it.isCompleted }
                    notifyDataSetChanged()
                } else {
                    // Set failed. Rollback checkbox state.
                    taskIsCompletedCheckBox.toggle()
                }
            }

            deleteTaskButton.setOnClickListener {
                if (interactionListener.deleteTask(task)) {
                    // Delete successful.
                    tasks.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, tasks.size)
                }
            }
        }

        private fun updateDisplay(task: SovietTask) {
            taskTitleTextView.text = task.title
            taskCreatedDateTimeTextView.text = task.created.format(DISPLAY_DATETIME_FORMATTER)
            taskDetailsTextView.text = task.details
            taskIsCompletedCheckBox.isChecked = task.isCompleted

            updateTextViewDisplay(taskTitleTextView, task.isCompleted)
            updateTextViewDisplay(taskDetailsTextView, task.isCompleted)
            updateTextViewDisplay(taskCreatedDateTimeTextView, task.isCompleted)
        }

        private fun updateTextViewDisplay(textView: TextView, isTaskCompleted: Boolean) {
            if (isTaskCompleted) {
                textView.setTextColor(context.getColor(R.color.light_gray))
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.setTextColor(context.getColor(R.color.dark_gray))
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    interface InteractionListener {
        fun setIsCompleted(task: SovietTask, newIsCompleted: Boolean): Boolean
        fun deleteTask(task: SovietTask): Boolean
    }
}
