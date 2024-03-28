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
class StatsFragment : Fragment(R.layout.fragment_stats) {
    @Inject
    lateinit var database: SovietDatabaseHelper

    private lateinit var statsAdapter: StatsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        initRecyclerView(view)
        initStats()
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.statsRecyclerView)
        statsAdapter = StatsAdapter(requireContext())
        recyclerView.adapter = statsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initStats() {
        val numPendingTasks = database.countTasks(false)
        val numCompletedTasks = database.countTasks(true)
        val numAllTasks = database.countTasks(null)
        val partyTaskReports = database.getTaskDeleteCount(SovietTask.Category.PartyTask)

        val stats = listOf(
            StatisticItem(
                icon = R.drawable.ic_stats_tasks_pending,
                label = R.string.label_stats_tasks_pending,
                value = numPendingTasks,
            ),
            StatisticItem(
                icon = R.drawable.ic_stats_tasks_completed,
                label = R.string.label_stats_tasks_completed,
                value = numCompletedTasks,
            ),
            StatisticItem(
                icon = R.drawable.ic_stats_tasks_all,
                label = R.string.label_stats_tasks_all,
                value = numAllTasks,
            ),
            StatisticItem(
                icon = R.drawable.ic_stats_reports,
                label = R.string.label_stats_party_tasks_reports,
                value = partyTaskReports
            )
        )

        statsAdapter.updateStats(stats)
    }
}
