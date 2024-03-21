package su.tovarischi.pyatiletka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class PropagandaDetailFragment(private val propaganda: SovietPropaganda) :
    DialogFragment(R.layout.fragment_propaganda_detail) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_background)
        return inflater.inflate(R.layout.fragment_propaganda_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageView: ImageView = view.findViewById(R.id.prop_image_detail)
        val titleTextView: TextView = view.findViewById(R.id.prop_title_detail)
        val descriptionTextView: TextView = view.findViewById(R.id.prop_desc_detail)

        val resources = view.resources
        titleTextView.text = resources.getString(propaganda.titleRes)
        descriptionTextView.text = resources.getString(propaganda.descriptionRes)
        imageView.setImageResource(propaganda.imageRes)
    }
}