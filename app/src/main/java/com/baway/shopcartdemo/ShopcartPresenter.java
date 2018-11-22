package com.baway.shopcartdemo;

import com.baway.shopcartdemo.entity.ShopCartBean;
import com.baway.shopcartdemo.utils.HttpCallBack;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopcartPresenter {
    private final ShopcartModel shopcartModel;
    private ShopcartVIew shopcartVIew;

    public ShopcartPresenter(ShopcartVIew shopcartVIew) {
        this.shopcartVIew = shopcartVIew;
        shopcartModel = new ShopcartModel();
    }

    public void getShopcart() {
        shopcartModel.getShopcart(new HttpCallBack() {
            @Override
            public void success(List<ShopCartBean.DataBean> dataBeans) {
                shopcartVIew.succe(dataBeans);
            }

            @Override
            public void fail() {
                shopcartVIew.fai("出错了");
            }
        });
    }
}
