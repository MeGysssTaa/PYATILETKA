package su.tovarischi.pyatiletka

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val context: Context,
    private val onTaskMarkedAsDone: (SovietTask) -> Unit,
    private val onTaskDeleted: (SovietTask) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val tasks = mutableListOf<SovietTask>()

    fun updateTasks(newTasks: List<SovietTask>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onTaskMarkedAsDone, onTaskDeleted)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.taskDetailsTextView)
        private val isCompletedCheckBox: CheckBox = itemView.findViewById(R.id.taskIsCompletedCheckBox)

        fun bind(task: SovietTask, onTaskMarkedAsDone: (SovietTask) -> Unit, onTaskDeleted: (SovietTask) -> Unit) {
            titleTextView.text = task.title
            detailsTextView.text = task.details
            isCompletedCheckBox.isChecked = task.isCompleted

            isCompletedCheckBox.setOnClickListener {
                println("is completed click!")
                //onTaskMarkedAsDone(task.copy(isDone = isCompletedCheckBox.isChecked))
            }
        }
    }
}