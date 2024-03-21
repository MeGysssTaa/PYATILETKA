package su.tovarischi.pyatiletka

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartyTasksFragment : TaskListFragment(), DeletePartyTaskFragment.OnTaskDeleteConfirmationListener {
    override val tasksCategory = SovietTask.Category.PartyTask

    override fun deleteTask(task: SovietTask): Boolean {

        val dialogFragment = DeletePartyTaskFragment(task, this)
        dialogFragment.show(requireActivity().supportFragmentManager, "DeletePartyTaskConfirmationDialogFragment")
        return false
    }

    override fun onTaskDeleteConfirmed(task: SovietTask) : Boolean {
        // TODO : count as deleted?
        return super.deleteTask(task)
    }
}
