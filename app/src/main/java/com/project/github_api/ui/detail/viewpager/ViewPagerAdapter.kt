package com.project.github_api.ui.detail.viewpager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.github_api.R
import com.project.github_api.ui.detail.follower.FollowerFragment
import com.project.github_api.ui.detail.following.FollowingFragment

class ViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    data: Bundle
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}