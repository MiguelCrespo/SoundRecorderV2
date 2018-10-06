package co.happydevelopers.soundrecorderv2.activities

import adapters.MainPagerAdapter
import android.net.Uri
import android.os.Bundle
import android.os.FileObserver
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import co.happydevelopers.soundrecorderv2.R
import fragments.RecordTabFragment
import com.google.android.material.tabs.TabLayout
import fragments.SavedRecordsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecordTabFragment.OnRecordTabFragmentInteractionListener, SavedRecordsFragment.OnSavedRecordsFragmentInteractionListener {
    override fun onAudioRecorded(fileName: String) {
        Log.d(LOG_TAG, "onAudioRecorded fileName: $fileName")

        val observer = object : FileObserver(fileName) {
            override fun onEvent(event: Int, path: String?) {
                Log.d(LOG_TAG, "EVENT: $event")
                Log.d(LOG_TAG, "Refreshing list")
                runOnUiThread { savedRecordsFragment.refreshList() }
                this.stopWatching()
            }
        }

        observer.startWatching()

    }

    private var mMainPagerAdapter : MainPagerAdapter? = null
    private val savedRecordsFragment = SavedRecordsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main_toolbar as Toolbar?)

        supportActionBar?.title = "Sound Recorder"

        initializeUi()
    }

    private fun initializeUi() {
        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager, savedRecordsFragment)

        viewPager_main_tab_container.adapter = mMainPagerAdapter

        viewPager_main_tab_container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout_main_main_tab_layout))
        tabLayout_main_main_tab_layout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager_main_tab_container))
    }

    companion object {
        val LOG_TAG: String = MainActivity::class.java.simpleName
    }
}
