package com.baway.shopcart2;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public interface HttpCallBack {
    void success(List<ShopCartBean.DataBean> dataBeans);
    void fail();
}
