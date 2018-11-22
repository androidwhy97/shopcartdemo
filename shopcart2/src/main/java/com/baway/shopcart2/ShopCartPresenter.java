package com.baway.shopcart2;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopCartPresenter {
    private final ShopCartModel shopCartModel;
    private ShopCartView shopCartView;

    public ShopCartPresenter(ShopCartView shopCartView) {
        this.shopCartView = shopCartView;
        shopCartModel = new ShopCartModel();
    }

    public void getShopCart(){
        shopCartModel.getShopCart(new HttpCallBack() {
            @Override
            public void success(List<ShopCartBean.DataBean> dataBeans) {
                shopCartView.successful(dataBeans);
            }

            @Override
            public void fail() {
                shopCartView.failer("出错了");
            }
        });
    }
}
