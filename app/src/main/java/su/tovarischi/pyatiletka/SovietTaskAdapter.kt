package su.tovarischi.pyatiletka

import android.annotation.SuppressLint
import android.content.Context
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
        val DISPLAY_DATE_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val DISPLAY_TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm")
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
        private val taskCreatedDateTextView: TextView = itemView.findViewById(R.id.taskCreatedDateTextView)
        private val taskCreatedTimeTextView: TextView = itemView.findViewById(R.id.taskCreatedTimeTextView)
        private val taskDetailsTextView: TextView = itemView.findViewById(R.id.taskDetailsTextView)
        private val taskIsCompletedCheckBox: CheckBox = itemView.findViewById(R.id.taskIsCompletedCheckBox)
        private val deleteTaskButton: ImageButton = itemView.findViewById(R.id.deleteTaskButton)

        fun bind(task: SovietTask, position: Int) {
            taskTitleTextView.text = task.title
            taskCreatedDateTextView.text = task.created.format(DISPLAY_DATE_FORMATTER)
            taskCreatedTimeTextView.text = task.created.format(DISPLAY_TIME_FORMATTER)
            taskDetailsTextView.text = task.details
            taskIsCompletedCheckBox.isChecked = task.isCompleted

            taskIsCompletedCheckBox.setOnClickListener {
                if (!interactionListener.setIsCompleted(task, taskIsCompletedCheckBox.isChecked)) {
                    taskIsCompletedCheckBox.toggle()
                }
            }

            deleteTaskButton.setOnClickListener {
                if (interactionListener.deleteTask(task)) {
                    tasks.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, tasks.size)
                }
            }
        }

    }

    interface InteractionListener {
        fun setIsCompleted(task: SovietTask, newIsCompleted: Boolean): Boolean
        fun deleteTask(task: SovietTask): Boolean
    }
}
