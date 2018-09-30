package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.happydevelopers.soundrecorderv2.RecordTabFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        return when(position) {
            0 -> RecordTabFragment.newInstance("123", "321")
            else -> RecordTabFragment.newInstance("123", "321")
        }
    }

    override fun getCount(): Int {
        return 2
    }

}