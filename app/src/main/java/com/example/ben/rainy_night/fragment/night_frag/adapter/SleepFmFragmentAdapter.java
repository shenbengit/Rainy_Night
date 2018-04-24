package com.example.ben.rainy_night.fragment.night_frag.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ben.rainy_night.fragment.night_frag.frag.SleepFmFragment;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;

    public SleepFmFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SleepFmFragment.newInstance(mTitles[position]);
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
