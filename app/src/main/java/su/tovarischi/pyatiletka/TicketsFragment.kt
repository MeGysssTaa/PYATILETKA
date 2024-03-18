package su.tovarischi.pyatiletka

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsFragment : TaskListFragment() {
    override val tasksCategory = SovietTask.Category.Ticket
}
