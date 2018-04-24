package com.example.ben.rainy_night.fragment.night_frag.presenter;

import com.example.ben.rainy_night.fragment.night_frag.contract.SleepFmContract;

/**
 *
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmPresenter implements SleepFmContract.Presenter {
    private SleepFmContract.View view;

    public SleepFmPresenter(SleepFmContract.View view) {
        this.view = view;
    }

}
