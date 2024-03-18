package su.tovarischi.pyatiletka

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTasksFragment : TaskListFragment() {
    override val tasksCategory = SovietTask.Category.HomeTask
}
