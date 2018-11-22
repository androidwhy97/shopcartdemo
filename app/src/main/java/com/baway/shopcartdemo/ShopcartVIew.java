package com.baway.shopcartdemo;

import com.baway.shopcartdemo.entity.ShopCartBean;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public interface ShopcartVIew {
    void succe(List<ShopCartBean.DataBean> dataBeans);
    void fai(String error);
}
