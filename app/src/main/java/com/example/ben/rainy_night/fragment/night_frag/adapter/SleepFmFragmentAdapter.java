package com.example.ben.rainy_night.fragment.night_frag.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ben.rainy_night.fragment.night_frag.frag.fm.SleepFmFragment;
import com.example.ben.rainy_night.util.Constant;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;

    private int[] mAlbums;

    public SleepFmFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
        mAlbums = new int[]{ Constant.DOLPHIN_BEFORE_SLEEP_AND_READ, Constant.DOLPHIN_NICE_PEOPLE,
                Constant.DOLPHIN_HYPNOSIS, Constant.DOLPHIN_SAY_GOOG_NIGHT};
    }

    @Override
    public Fragment getItem(int position) {
        return SleepFmFragment.newInstance(mAlbums[position]);
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
