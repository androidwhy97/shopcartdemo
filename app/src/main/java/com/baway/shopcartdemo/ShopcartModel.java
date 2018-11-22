package com.baway.shopcartdemo;

import android.os.Handler;
import android.os.Message;

import com.baway.shopcartdemo.entity.ShopCartBean;
import com.baway.shopcartdemo.utils.HttpCallBack;
import com.baway.shopcartdemo.utils.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopcartModel {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void getShopcart(final HttpCallBack httpCallBack) {
        OkHttpUtil.enqueueGet("http://120.27.23.105/product/getCarts?source=android&uid=99", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.fail();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String string = body.string();
                Gson gson = new Gson();
                ShopCartBean shopCartBean = gson.fromJson(string, ShopCartBean.class);
                final List<ShopCartBean.DataBean> dataBeans = shopCartBean.getData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.success(dataBeans);
                    }
                });

            }
        });
    }
}
