package fragments

import adapters.AudioListAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.fragment_saved_records.*
import kotlinx.android.synthetic.main.fragment_saved_records.view.*
import java.io.File
import com.google.android.material.tabs.TabLayout
import co.happydevelopers.soundrecorderv2.activities.MainActivity
import androidx.core.content.FileProvider




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
    private var listener: OnSavedRecordsFragmentInteractionListener? = null
    private val mSoundList = arrayListOf<File>()
    private var mRecyclerViewAdapter: AudioListAdapter? = null

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEmptyView: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_saved_records, container, false)
        // Inflate the layout for this fragment

        mRecyclerView = v.recyclerView_saved_records_audio_list
        mEmptyView = v.linearLayout_saved_records_empty_state_container

        readFiles()

        initializeUI()

        v.swipeRefresh_saved_records_refresh.setOnRefreshListener {
            Log.d(LOG_TAG, "OnRefreshListener")
            refreshList()
            // Signal SwipeRefreshLayout to start the progress indicator
            v.swipeRefresh_saved_records_refresh.isRefreshing = false
        }

        v.button_saved_records_start_recording.setOnClickListener {
            val tabHost = activity?.findViewById(R.id.tabLayout_main_main_tab_layout) as TabLayout
            tabHost.getTabAt(0)?.select()
        }

        return v
    }

    private fun initializeUI() {
        if (mSoundList.size > 0) {
            mEmptyView.visibility = View.GONE

            val layoutManager = LinearLayoutManager(activity)

            mRecyclerView.layoutManager = layoutManager

            mRecyclerViewAdapter = AudioListAdapter(mSoundList)
            mRecyclerView.adapter = mRecyclerViewAdapter
        } else {
            mEmptyView.visibility = View.VISIBLE
        }
    }

    private fun readFiles() {
        Log.d(LOG_TAG, "readFiles $AUDIO_PATH")
        File(AUDIO_PATH).walk().forEach {
            if (it.isFile) {
                mSoundList.add(it)
            }
        }
    }

    fun refreshList() {
        Log.d(LOG_TAG, "Refreshing list...")
        mSoundList.clear()

        readFiles()

        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter?.setFiles(mSoundList)

            mRecyclerViewAdapter?.notifyDataSetChanged()
        }

        initializeUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSavedRecordsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        Log.d(LOG_TAG, "Option selected: " + item?.itemId)
        when (item?.groupId) {
            0 -> shareFile(item.itemId)
            1 -> {
                deleteFile(item.itemId)
            }
        }

        return super.onContextItemSelected(item)
    }

    private fun shareFile(index: Int) {
        val file = mSoundList[index]

        val shareIntent = Intent()

        val fileUri = FileProvider.getUriForFile(
                activity!!,
                "com.happydevelopers.soundrecorderv2.provider", //(use your app signature + ".provider" )
                file)

        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.type = "audio/*"

        context?.startActivity(Intent.createChooser(shareIntent, "Send to"))
    }

    private fun deleteFile(index: Int) {
        val file = mSoundList[index]

        file.delete()

        Toast.makeText(activity, "File ${file.name} deleted", Toast.LENGTH_LONG).show()

        refreshList()
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
    interface OnSavedRecordsFragmentInteractionListener {

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
