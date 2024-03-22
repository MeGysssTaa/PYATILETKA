package su.tovarischi.pyatiletka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class DeletePartyTaskFragment(
    private val task: SovietTask
) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_background)
        return inflater.inflate(R.layout.fragment_delete_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val okButton =  view.findViewById<Button>(R.id.dialog_ok_button)

        okButton.setOnClickListener {
            dismiss()
        }
    }
}