package com.example.ben.rainy_night.fragment.main_frag.presenter;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.home_frag.frag.HomeFragment;
import com.example.ben.rainy_night.fragment.main_frag.contract.MainContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.MineFragment;
import com.example.ben.rainy_night.fragment.night_frag.frag.NightFragment;
import com.example.ben.rainy_night.fragment.share_frag.frag.ShareFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/1/8
 */

public class MainPresenterImpl implements MainContract.Presenter {
    private MainContract.View view;

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    public MainPresenterImpl(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void loadRootFragment() {
        SupportFragment firstFragment = view.findChildFrag(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = NightFragment.newInstance();
            mFragments[THIRD] = ShareFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();
            view.loadMultipleRootFrag(R.id.container_activity_main, FIRST,
                    mFragments[FIRST], mFragments[SECOND],
                    mFragments[THIRD], mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = view.findChildFrag(HomeFragment.class);
            mFragments[SECOND] = view.findChildFrag(NightFragment.class);
            mFragments[THIRD] = view.findChildFrag(ShareFragment.class);
            mFragments[FOURTH] = view.findChildFrag(MineFragment.class);
        }
    }

    @Override
    public void showHideFragment(int position, int prePosition) {
        view.showHideFrag(mFragments[position], mFragments[prePosition]);
    }
}
