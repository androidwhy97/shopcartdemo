package com.baway.shopcartdemo.fragment;

import android.app.Activity;
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

import com.baway.shopcartdemo.AddOrSubView;
import com.baway.shopcartdemo.R;
import com.baway.shopcartdemo.ShopcartPresenter;
import com.baway.shopcartdemo.ShopcartVIew;
import com.baway.shopcartdemo.adapter.ShopcartExpanListAdapter;
import com.baway.shopcartdemo.entity.ShopCartBean;

import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopcartExpanableFragment extends Fragment implements ShopcartVIew {
    private Activity mActivity;
    private View rootView;
    private TextView tvSum;
    private CheckBox cbCheckAll;
    private ExpandableListView expandableListView;
    private ShopcartPresenter shopcartPresenter;
    private ShopcartExpanListAdapter expanListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shop_cart, null);
        expandableListView = rootView.findViewById(R.id.expanListView);
        expandableListView.setGroupIndicator(null);
        tvSum = rootView.findViewById(R.id.tvSum);
        cbCheckAll = rootView.findViewById(R.id.cbCheckAll);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shopcartPresenter = new ShopcartPresenter(this);

        initData();

        cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        int groupCount = expanListAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            ShopCartBean.DataBean group = (ShopCartBean.DataBean) expanListAdapter.getGroup(i);
            List<ShopCartBean.DataBean.ListBean> list = group.getList();

            for (int j = 0; j < list.size(); j++) {
                ShopCartBean.DataBean.ListBean listBean = list.get(j);
                listBean.setSelected(s);
            }
        }

        expanListAdapter.notifyDataSetChanged();
        getTotal();
    }

    private void getTotal() {

        double total = 0;
        int groupCount = expanListAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            ShopCartBean.DataBean group = (ShopCartBean.DataBean) expanListAdapter.getGroup(i);
            List<ShopCartBean.DataBean.ListBean> list = group.getList();
            for (int j = 0; j < list.size(); j++) {
                ShopCartBean.DataBean.ListBean listBean = list.get(j);

                boolean checked = listBean.isChecked();
                if (checked) {
                    double price = listBean.getPrice();
                    total += price * listBean.getNum();
                }

                tvSum.setText("合计: " + total);
            }
        }
    }

    private void initData() {
        shopcartPresenter.getShopcart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void succe(List<ShopCartBean.DataBean> dataBeans) {
        if (expanListAdapter == null) {
            expanListAdapter = new ShopcartExpanListAdapter(dataBeans, getActivity());
            expandableListView.setAdapter(expanListAdapter);
        }

        for (int i = 0; i < expanListAdapter.getGroupCount(); i++) {
            //展开辅助
            expandableListView.expandGroup(i);
        }

        initShopcartChange();
    }

    private void initShopcartChange() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ShopCartBean.DataBean group = (ShopCartBean.DataBean) expanListAdapter.getGroup(groupPosition);
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
                expanListAdapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ShopCartBean.DataBean.ListBean child = (ShopCartBean.DataBean.ListBean) expanListAdapter.getChild(groupPosition, childPosition);
                boolean checked = child.isChecked();
                if (checked) {
                    child.setSelected(0);
                } else {
                    child.setSelected(1);
                }

                expanListAdapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });

        expanListAdapter.setOnNumChangedListener(new AddOrSubView.onNumChangedListener() {
            @Override
            public void onNumChanged(View view, int curnum) {
                getTotal();
            }
        });
    }

    @Override
    public void fai(String error) {

    }
}
