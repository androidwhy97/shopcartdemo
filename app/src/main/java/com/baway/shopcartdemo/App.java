package com.baway.shopcartdemo;

import android.app.Application;

import com.baway.shopcartdemo.utils.OkHttpUtil;

import okhttp3.OkHttpClient;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtil.init();
    }
}
