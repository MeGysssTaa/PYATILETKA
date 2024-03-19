package su.tovarischi.pyatiletka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class DeletePartyTaskConfirmationDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_delete_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val deleteButton =  view.findViewById<Button>(R.id.dialog_delete_button)
        val cancelButton = view.findViewById<Button>(R.id.dialog_cancel_button)

        cancelButton.setOnClickListener {
            dismiss()
        }

        deleteButton.setOnClickListener{


            dismiss()
        }
    }
}