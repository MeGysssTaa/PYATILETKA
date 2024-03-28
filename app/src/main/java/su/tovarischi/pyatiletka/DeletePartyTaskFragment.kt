package su.tovarischi.pyatiletka

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class DeletePartyTaskFragment(
    private val task: SovietTask
) : DialogFragment() {

    private var alertMediaPlayer: MediaPlayer? = null

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
        val view = inflater.inflate(R.layout.fragment_delete_notification, container, false)

        alertMediaPlayer = MediaPlayer.create(requireContext(), R.raw.alert)
        alertMediaPlayer?.setVolume(100f, 100f)
        alertMediaPlayer?.start()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val okButton =  view.findViewById<Button>(R.id.dialog_ok_button)

        okButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        alertMediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        alertMediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        alertMediaPlayer?.release()
    }
}