package fragments

import adapters.AudioListAdapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.fragment_saved_records.view.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SavedRecordsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SavedRecordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SavedRecordsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    private val mSoundList = arrayListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_saved_records, container, false)
        // Inflate the layout for this fragment

        readFiles()

        if (mSoundList.size > 0) {
            v.linearLayout_saved_records_empty_state_container.visibility = View.GONE

            v.recyclerView_saved_records_audio_list.layoutManager = LinearLayoutManager(activity)
            v.recyclerView_saved_records_audio_list.adapter = AudioListAdapter(mSoundList)
        }

        return v
    }

    private fun readFiles() {
        File(AUDIO_PATH).walk().forEach {
            val file = File(it.absolutePath)
            if (file.isFile) {
                mSoundList.add(file)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SavedRecordsFragment.
         */

        val LOG_TAG = SavedRecordsFragment::class.java.simpleName

        val AUDIO_PATH = Environment.getExternalStorageDirectory().absolutePath + "/com.happydevelopers.soundRecorderV2/"

        @JvmStatic
        fun newInstance() =
                SavedRecordsFragment()
    }
}
