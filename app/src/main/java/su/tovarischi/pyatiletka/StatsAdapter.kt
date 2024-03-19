package su.tovarischi.pyatiletka

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import su.tovarischi.pyatiletka.databinding.StatListItemBinding

class StatsAdapter(
    val context: Context,
) : RecyclerView.Adapter<StatsAdapter.StatViewHolder>() {
    private val stats = mutableListOf<StatisticItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateStats(newStats: List<StatisticItem>) {
        stats.clear()
        stats += newStats
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val binding = StatListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val statItem = stats[position]
        holder.binding.iconImageView.setImageResource(statItem.icon)
        holder.binding.titleTextView.text = context.getString(statItem.label)
        holder.binding.numberTextView.text = statItem.value.toString()
    }

    override fun getItemCount(): Int = stats.size

    inner class StatViewHolder(val binding: StatListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
