package co.happydevelopers.soundrecorderv2.activities

import adapters.MainPagerAdapter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import co.happydevelopers.soundrecorderv2.R
import fragments.RecordTabFragment
import com.google.android.material.tabs.TabLayout
import fragments.SavedRecordsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecordTabFragment.OnRecordTabFragmentInteractionListener, SavedRecordsFragment.OnSavedRecordsFragmentInteractionListener {
    override fun onAudioRecorded() {
        Log.d(LOG_TAG, "onAudioRecorded")

        savedRecordsFragment.refreshList()
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
        val LOG_TAG = MainActivity::class.java.simpleName
    }
}
