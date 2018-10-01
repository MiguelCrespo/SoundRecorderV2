package co.happydevelopers.soundrecorderv2.activities

import adapters.MainPagerAdapter
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import co.happydevelopers.soundrecorderv2.R
import fragments.RecordTabFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecordTabFragment.OnFragmentInteractionListener {
    private var mMainPagerAdapter : MainPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main_toolbar as Toolbar?)

        supportActionBar?.title = "Sound Recorder"

        initializeUi()
    }

    private fun initializeUi() {
        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager)

        viewPager_main_tab_container.adapter = mMainPagerAdapter

        viewPager_main_tab_container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout_main_main_tab_layout))
        tabLayout_main_main_tab_layout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager_main_tab_container))
    }
}
