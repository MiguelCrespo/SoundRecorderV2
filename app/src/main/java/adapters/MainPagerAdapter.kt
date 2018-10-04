package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fragments.RecordTabFragment
import fragments.SavedRecordsFragment

class MainPagerAdapter(fm: FragmentManager, private val savedRecordsFragment: SavedRecordsFragment) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        return when(position) {
            0 -> RecordTabFragment.newInstance()
            1 -> savedRecordsFragment
            else -> RecordTabFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

}