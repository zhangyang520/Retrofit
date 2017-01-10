package com.example.zhanyang.retrofitdemo2.dragger.module;

import com.example.zhanyang.retrofitdemo2.MainActivity;
import com.example.zhanyang.retrofitdemo2.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyang on 2017/1/7 22:32.
 * version 1
 */
@Module
public class MainActivityModule {
    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    @Provides
    MainActivity provideMainActivity() {
        return mainActivity;
    }

    @Provides
    MainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenter(mainActivity);
    }
}
