package su.tovarischi.pyatiletka

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartyTasksFragment : TaskListFragment(), DeletePartyTaskFragment.InteractionListener {
    override val tasksCategory = SovietTask.Category.PartyTask

    override fun deleteTask(task: SovietTask): Boolean {

        val dialogFragment = DeletePartyTaskFragment(task, this)
        dialogFragment.show(requireActivity().supportFragmentManager, "DeletePartyTaskConfirmationDialogFragment")
        return false
    }

    override fun onTaskDeleteAttempt(task: SovietTask) {
        // TODO : count as deleted?
    }
}
