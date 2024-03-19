package su.tovarischi.pyatiletka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class TaskListFragment : Fragment(), SovietTaskAdapter.InteractionListener {
    protected abstract val tasksCategory: SovietTask.Category

    @Inject
    protected lateinit var database: SovietDatabaseHelper

    private lateinit var sovietTaskAdapter: SovietTaskAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var createTaskButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        initRecyclerView(view)
        initTasks()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createTaskButton = view.findViewById(R.id.createTaskButton)
        createTaskButton.setOnClickListener { createTask() }
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        sovietTaskAdapter = SovietTaskAdapter(requireContext(), this)
        recyclerView.adapter = sovietTaskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initTasks() {
        val tasks = database.getTasks(tasksCategory)
        sovietTaskAdapter.updateTasks(tasks)
    }

    private fun createTask() {
        val args = Bundle().apply {
            putString("task_category", tasksCategory.name)
            putStringArrayList("available_categories", SovietTask.Category.entries.map { it.name }.toCollection(ArrayList()))
        }
        val addTaskBottomSheetFragment = AddTaskBottomSheetFragment()
        addTaskBottomSheetFragment.arguments = args
        addTaskBottomSheetFragment.show(requireActivity().supportFragmentManager, "AddTaskBottomSheetFragment")
    }

    override fun setIsCompleted(task: SovietTask, newIsCompleted: Boolean): Boolean {
        task.isCompleted = newIsCompleted
        return database.updateIsCompleted(task.taskId, newIsCompleted)
    }

    override fun deleteTask(task: SovietTask): Boolean {
        return database.deleteTask(task.taskId)
    }
}
