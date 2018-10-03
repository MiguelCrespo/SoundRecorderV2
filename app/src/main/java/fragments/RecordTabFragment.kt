package fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.fragment_record_tab.*
import kotlinx.android.synthetic.main.fragment_record_tab.view.*
import services.RecordService
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecordTabFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecordTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RecordTabFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record_tab, container, false)

        v.button_record_fragment_record.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            } else {
                requestAudioPermission()
            }
        }

        return v
    }

    private fun requestAudioPermission() {
        requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), AUDIO_PERMISSION_GRANTED)
    }

    private fun startRecording() {
        val intent = Intent(activity, RecordService::class.java)

        if (!isRecording) {
            chronometer_record_fragment_chronometer.base = SystemClock.elapsedRealtime()
            chronometer_record_fragment_chronometer.start()

            textView_record_fragment_bottom_status.text = "Recording..."

            button_record_fragment_record.text = "Stop"

            activity?.startService(intent)
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        } else {
            chronometer_record_fragment_chronometer.stop()

            // Why is this line needed?
            chronometer_record_fragment_chronometer.base = SystemClock.elapsedRealtime()
            textView_record_fragment_bottom_status.text = "Tap the button to start recording"

            button_record_fragment_record.text = "Record"

            activity?.stopService(intent)
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        isRecording = !isRecording
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == AUDIO_PERMISSION_GRANTED) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            } else {
                Toast.makeText(context, "Please grant permission to start recording", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
    }

    companion object {
        const val AUDIO_PERMISSION_GRANTED = 1
        val LOG_TAG = RecordTabFragment::class.java.simpleName
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecordTabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                RecordTabFragment()
    }
}
