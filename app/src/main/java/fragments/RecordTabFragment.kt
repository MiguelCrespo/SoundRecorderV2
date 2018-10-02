package fragments

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.fragment_record_tab.view.*
import java.io.IOException


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_record_tab, container, false)

        v.button_record_fragment_record.setOnClickListener {
            startRecording()
        }

        return v
    }

    private fun startRecording() {
        // Just testing what happens if I start to record an audio in the fragment and without permission
        val fileName = Environment.getExternalStorageDirectory().absolutePath + "/ElMigue" + "Test"

        val mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioChannels(1)
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(fileName)

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (e: IOException) {
            Log.e("ERROR", "prepare() failed");
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
