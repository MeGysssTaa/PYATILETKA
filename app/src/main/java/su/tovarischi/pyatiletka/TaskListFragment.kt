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
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
abstract class TaskListFragment : Fragment(), SovietTaskAdapter.InteractionListener, AddTaskBottomSheetFragment.InteractionListener {
    protected abstract val tasksCategory: SovietTask.Category

    @Inject
    protected lateinit var database: SovietDatabaseHelper

    private lateinit var sovietTaskAdapter: SovietTaskAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var createTaskButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        initRecyclerView(view)
        initTasks() // load initial task list
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createTaskButton = view.findViewById(R.id.createTaskButton)
        createTaskButton.setOnClickListener {
            createTask()
        }
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.tasksRecyclerView)
        sovietTaskAdapter = SovietTaskAdapter(requireContext(), this)
        recyclerView.adapter = sovietTaskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initTasks() {
        val tasks = database.getTasks(tasksCategory)
        sovietTaskAdapter.updateTasks(tasks)
    }

    private fun createTask() {
        val addTaskBottomSheetFragment = AddTaskBottomSheetFragment(tasksCategory, SovietTask.Category.entries, this)
        addTaskBottomSheetFragment.show(requireActivity().supportFragmentManager, "AddTaskBottomSheetFragment")
    }

    override fun setIsCompleted(task: SovietTask, newIsCompleted: Boolean): Boolean {
        task.isCompleted = newIsCompleted
        return database.updateIsCompleted(task.taskId, newIsCompleted)
    }

    override fun deleteTask(task: SovietTask): Boolean {
        return database.deleteTask(task.taskId)
    }

    override fun saveNewTask(
        taskName: String,
        taskDescription: String,
        taskCategory: SovietTask.Category?
    ) {
        val newTaskCategory = taskCategory ?: tasksCategory

        val newTask = SovietTask(
            taskId = UUID.randomUUID(),
            created = LocalDateTime.now(),
            category = newTaskCategory,
            title = taskName,
            details = taskDescription,
            isCompleted = false,
        )

        database.createTask(newTask)
        initTasks() // handle update
    }
}
