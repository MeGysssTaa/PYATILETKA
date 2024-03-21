package su.tovarischi.pyatiletka

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class PropagandaAdapter(
    private val items: List<SovietPropaganda>,
    val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<PropagandaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.propaganda_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.propaganda_image)
        private val titleTextView: TextView = itemView.findViewById(R.id.propaganda_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.propaganda_description)
        fun bind(item: SovietPropaganda) {
            val resources = itemView.resources
            titleTextView.text = resources.getString(item.titleRes)
            descriptionTextView.text = resources.getString(item.descriptionRes)
            imageView.setImageResource(item.imageRes)

            itemView.setOnClickListener {
                val dialogFragment = PropagandaDetailFragment(item)
                dialogFragment.show(fragmentManager, "ItemDetailDialogFragment")
            }
        }
    }
}