package com.example.ben.rainy_night.fragment.home_frag.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ben.rainy_night.fragment.home_frag.frag.music.SleepMusicListFragment;

/**
 *
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    public SleepMusicFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SleepMusicListFragment.newInstance("1");
        } else if (position == 1) {
            return SleepMusicListFragment.newInstance("2");
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
