package com.baway.shopcart2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopCartFragment extends Fragment implements ShopCartView {

    private View rootView;
    private ExpandableListView mExpandList;
    private CheckBox cBoxAll;
    private TextView textSum;
    private ShopCartPresenter shopCartPresenter;
    private ShopCartAdapter shopCartAdapter;

    @Override

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shopcart, null);
        mExpandList = rootView.findViewById(R.id.expand_listview);
        cBoxAll = rootView.findViewById(R.id.cb_all);
        textSum = rootView.findViewById(R.id.txt_sum);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shopCartPresenter = new ShopCartPresenter(this);

        shopCartPresenter.getShopCart();

        cBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCheckAll(1);
                } else {
                    setCheckAll(0);
                }
            }
        });
    }

    private void setCheckAll(int s) {
        int groupCount = shopCartAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            ShopCartBean.DataBean group = (ShopCartBean.DataBean) shopCartAdapter.getGroup(i);
            List<ShopCartBean.DataBean.ListBean> list = group.getList();
            for (int j = 0; j < list.size(); j++) {
                ShopCartBean.DataBean.ListBean listBean = list.get(j);
                listBean.setSelected(s);
            }
        }
        shopCartAdapter.notifyDataSetChanged();
        getTotal();
    }

    private void initNumchanged() {
        mExpandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ShopCartBean.DataBean group = (ShopCartBean.DataBean) shopCartAdapter.getGroup(groupPosition);
                group.setChecked(!group.isChecked());
                int c = 0;
                if (group.isChecked()) {
                    c = 1;
                }
                List<ShopCartBean.DataBean.ListBean> list = group.getList();
                for (int i = 0; i < list.size(); i++) {
                    ShopCartBean.DataBean.ListBean listBean = list.get(i);
                    listBean.setSelected(c);
                }
                shopCartAdapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });
        mExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ShopCartBean.DataBean.ListBean child = (ShopCartBean.DataBean.ListBean) shopCartAdapter.getChild(groupPosition, childPosition);
                boolean checked = child.isChecked();
                if (checked) {
                    child.setSelected(0);
                } else {
                    child.setSelected(1);
                }
                shopCartAdapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });

        shopCartAdapter.setOnNumChangedListener(new AddAndSubView.OnNumChangedListener() {
            @Override
            public void onNumChanged(View v, int currentNum) {
                getTotal();
            }
        });
    }

    private void getTotal() {
        double total = 0;
        int groupCount = shopCartAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            ShopCartBean.DataBean group = (ShopCartBean.DataBean) shopCartAdapter.getGroup(i);
            List<ShopCartBean.DataBean.ListBean> list = group.getList();

            for (int j = 0; j < list.size(); j++) {
                ShopCartBean.DataBean.ListBean listBean = list.get(j);
                boolean checked = listBean.isChecked();
                if (checked) {
                    double price = listBean.getPrice();
                    total += price * listBean.getNum();
                }
            }
        }
        textSum.setText("合计: " + total);
    }

    @Override
    public void successful(List<ShopCartBean.DataBean> dataBeans) {
        if (shopCartAdapter == null) {
            shopCartAdapter = new ShopCartAdapter(getContext(), dataBeans);
            mExpandList.setAdapter(shopCartAdapter);
        }

        //展开
        int groupCount = shopCartAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            mExpandList.expandGroup(i);
            mExpandList.setGroupIndicator(null);
        }

        initNumchanged();
    }

    @Override
    public void failer(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
