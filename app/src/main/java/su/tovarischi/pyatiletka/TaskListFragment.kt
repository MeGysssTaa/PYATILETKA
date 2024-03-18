package su.tovarischi.pyatiletka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class TaskListFragment : Fragment() {
    protected abstract val tasksCategory: SovietTask.Category

    @Inject
    lateinit var database: SovietDatabaseHelper

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        initRecyclerView(view)
        loadTasks()
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter(
            context = requireContext(),
            onTaskMarkedAsDone = ::onTaskMarkedAsDone,
            onTaskDeleted = ::onTaskDeleted,
        )
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadTasks() {
        val tasks = database.getTasks(tasksCategory)
        taskAdapter.updateTasks(tasks)
    }

    private fun onTaskMarkedAsDone(task: SovietTask) {

    }

    private fun onTaskDeleted(task: SovietTask) {

    }
}