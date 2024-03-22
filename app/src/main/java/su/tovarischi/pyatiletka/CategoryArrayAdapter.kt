package su.tovarischi.pyatiletka

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CategoryArrayAdapter(
    context: Context,
    layoutId : Int,
    categoriesList: List<SovietTask.Category>
) : ArrayAdapter<SovietTask.Category>(context, layoutId, categoriesList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val category = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        category?.let {
            textView.text = context.resources.getString(it.label)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val category = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        category?.let {
            textView.text = context.resources.getString(it.label)
        }
        return view
    }
}