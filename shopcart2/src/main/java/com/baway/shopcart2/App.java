package com.baway.shopcart2;

import android.app.Application;

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
