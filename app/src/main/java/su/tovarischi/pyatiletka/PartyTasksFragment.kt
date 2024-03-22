package su.tovarischi.pyatiletka

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartyTasksFragment : TaskListFragment(){
    override val tasksCategory = SovietTask.Category.PartyTask

    override fun deleteTask(task: SovietTask): Boolean {

        val dialogFragment = DeletePartyTaskFragment(task)
        dialogFragment.show(requireActivity().supportFragmentManager, "DeletePartyTaskConfirmationDialogFragment")

        super.database.addTaskDelete(tasksCategory)

        return false
    }
}
