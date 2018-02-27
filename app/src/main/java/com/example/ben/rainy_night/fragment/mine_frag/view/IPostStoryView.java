package com.example.ben.rainy_night.fragment.mine_frag.view;

import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.GridView;

import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/2/22
 */

public interface IPostStoryView extends BaseView {
    /**
     * 获取Activity
     *
     * @return
     */
    FragmentActivity getFragAct();

    /**
     * @return GridView
     */
    GridView getGridView();

    /**
     * @return EditText
     */
    EditText getEditText();
}
