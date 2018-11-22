package com.baway.shopcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.shopcartdemo.AddOrSubView;
import com.baway.shopcartdemo.R;
import com.baway.shopcartdemo.entity.ShopCartBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopcartExpanListAdapter extends BaseExpandableListAdapter {
    private List<ShopCartBean.DataBean> listData = new ArrayList<>();
    private Context mContext;
    private AddOrSubView.onNumChangedListener onNumChangedListener;

    public ShopcartExpanListAdapter(List<ShopCartBean.DataBean> listData, Context mContext) {
        if (listData != null) {
            this.listData.addAll(listData);
        }
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = View.inflate(mContext, R.layout.item_shopcart_expanable_group, null);
            groupViewHolder.checkBox = convertView.findViewById(R.id.checkbox);
            groupViewHolder.shopName = convertView.findViewById(R.id.tvShopName);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.shopName.setText(listData.get(groupPosition).getSellerName());
        groupViewHolder.checkBox.setChecked(listData.get(groupPosition).isChecked());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(mContext, R.layout.item_shopcart_child, null);
            childViewHolder.cbChecked = convertView.findViewById(R.id.cbChecked);
            childViewHolder.tvGoodsName = convertView.findViewById(R.id.tvGoodsName);
            childViewHolder.tvGoodsPrice = convertView.findViewById(R.id.tvGoodsPrice);
            childViewHolder.ivGoodsIcon = convertView.findViewById(R.id.ivGoodsIcon);
            childViewHolder.addOrSubView = convertView.findViewById(R.id.addSubView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final ShopCartBean.DataBean.ListBean listBean = listData.get(groupPosition).getList().get(childPosition);

        childViewHolder.cbChecked.setChecked(listBean.isChecked());
        childViewHolder.tvGoodsName.setText(listBean.getTitle());
        childViewHolder.tvGoodsPrice.setText(listBean.getPrice() + "");
        childViewHolder.addOrSubView.setCurrentcount(listBean.getNum());

        String images = listBean.getImages();
        String[] split = images.split("\\|");
        if (split.length > 0) {
            Picasso.with(mContext).load(split[0]).into(childViewHolder.ivGoodsIcon);
        }

        childViewHolder.addOrSubView.setonNumChangedListener(new AddOrSubView.onNumChangedListener() {
            @Override
            public void onNumChanged(View view, int curnum) {
                listBean.setNum(curnum);
                if (onNumChangedListener != null) {
                    onNumChangedListener.onNumChanged(view, curnum);
                }
            }
        });
        return convertView;
    }

    public void setOnNumChangedListener(AddOrSubView.onNumChangedListener onNumChangedListener) {
        this.onNumChangedListener = onNumChangedListener;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        CheckBox checkBox;
        TextView shopName;
    }

    class ChildViewHolder {
        CheckBox cbChecked;

        ImageView ivGoodsIcon;

        TextView tvGoodsName, tvGoodsPrice;

        AddOrSubView addOrSubView;
    }
}
