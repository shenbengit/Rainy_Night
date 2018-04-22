package com.example.ben.rainy_night.fragment.home_frag.frag;


import android.view.View;
import android.widget.Button;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.frag.music.SleepMusicFragment;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.GsonUtil;
import com.example.ben.rainy_night.util.LoggerUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * @author Ben
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.btn_home_sleep)
    Button btnHomeSleep;

    @OnClick({R.id.btn_home_sleep})
    public void viewOnClick(View view) {
        assert (getParentFragment()) != null;
        switch (view.getId()) {
            case R.id.btn_home_sleep:
                ((MainFragment) getParentFragment()).startBrotherFragment(SleepMusicFragment.newInstance());
                break;
            default:
                break;
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        new Thread(() -> {
            BmobQuery<SleepMusicEntity> query = new BmobQuery<>();
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.findObjects(new FindListener<SleepMusicEntity>() {
                @Override
                public void done(List<SleepMusicEntity> list, BmobException e) {
                    if (e == null) {
                        LoggerUtil.e("睡眠音乐大小： " + list.size());
                        for (int i = 0; i < list.size(); i++) {
                            MusicEntity entity = GsonUtil.fromJson(list.get(i).getJson(), MusicEntity.class);
                            LoggerUtil.e(list.get(i).getTitle() + "大小: " + entity.getData().size());

                        }
                    }
                }
            });
        }).start();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
