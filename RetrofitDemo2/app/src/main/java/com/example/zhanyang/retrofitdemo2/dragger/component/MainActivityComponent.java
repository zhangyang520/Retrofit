package com.example.zhanyang.retrofitdemo2.dragger.component;

import com.example.zhanyang.retrofitdemo2.MainActivity;
import com.example.zhanyang.retrofitdemo2.dragger.module.MainActivityModule;
import com.example.zhanyang.retrofitdemo2.presenter.MainActivityPresenter;

import dagger.Component;

/**
 * Created by zhangyang on 2017/1/7 22:28.
 * version 1
 */
//通过接口创建实例的代码和目标实例进行关联在一起
@Component(modules= MainActivityModule.class)
public interface  MainActivityComponent {
    void in(MainActivity mainActivity);
}
