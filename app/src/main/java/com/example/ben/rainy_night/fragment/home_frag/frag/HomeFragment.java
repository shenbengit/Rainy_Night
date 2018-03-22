package com.example.ben.rainy_night.fragment.home_frag.frag;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.http.rxhttp.entity.BaseEntity;
import com.example.ben.rainy_night.http.rxhttp.entity.MusicEntity;
import com.example.ben.rainy_night.http.rxhttp.factory.RetrofitFactory;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author Ben
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.home_fragment;
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
        RetrofitFactory.getInstance().getMusic(Constant.HAITUN_NATURAL_MUSIC,
                new Observer<BaseEntity<MusicEntity>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseEntity<MusicEntity> musicEntityBaseEntity) {
                LoggerUtil.e("状态码: " + musicEntityBaseEntity.getCode());
                MusicEntity entity = musicEntityBaseEntity.getData();
                LoggerUtil.e("音乐长度: " + entity.getData().size());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
