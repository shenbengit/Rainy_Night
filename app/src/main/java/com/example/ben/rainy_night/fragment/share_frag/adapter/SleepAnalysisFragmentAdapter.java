package com.example.ben.rainy_night.fragment.share_frag.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ben.rainy_night.fragment.share_frag.frag.analysis.SleepReadFragment;
import com.example.ben.rainy_night.fragment.share_frag.frag.analysis.SleepReportFragment;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepAnalysisFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    public SleepAnalysisFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SleepReadFragment.newInstance(mTitles[position]);
        } else if (position == 1) {
            return SleepReportFragment.newInstance(mTitles[position]);
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
