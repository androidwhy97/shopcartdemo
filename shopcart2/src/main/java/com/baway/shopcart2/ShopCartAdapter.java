package com.baway.shopcart2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class ShopCartAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ShopCartBean.DataBean> dataBeans = new ArrayList<>();
    private AddAndSubView.OnNumChangedListener listener;

    public ShopCartAdapter(Context context, List<ShopCartBean.DataBean> dataBeans) {
        this.context = context;
        if (dataBeans != null && dataBeans.size() > 0) {
            this.dataBeans.addAll(dataBeans);
        }
    }

    @Override
    public int getGroupCount() {
        return dataBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataBeans.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataBeans.get(groupPosition).getList().get(childPosition);
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
        ViewHolder1 viewHolder1;
        if (convertView == null) {
            viewHolder1 = new ViewHolder1();
            convertView = View.inflate(context, R.layout.item_group, null);
            viewHolder1.textView = convertView.findViewById(R.id.tvshopname);
            viewHolder1.checkBox = convertView.findViewById(R.id.cb_shop);
            convertView.setTag(viewHolder1);
        } else {
            viewHolder1 = (ViewHolder1) convertView.getTag();
        }
        viewHolder1.textView.setText(dataBeans.get(groupPosition).getSellerName());
        viewHolder1.checkBox.setChecked(dataBeans.get(groupPosition).isChecked());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder2;
        if (convertView == null) {
            viewHolder2 = new ViewHolder2();
            convertView = View.inflate(context, R.layout.item_child, null);
            viewHolder2.ccche = convertView.findViewById(R.id.cche);
            viewHolder2.goodsName = convertView.findViewById(R.id.tv_goodsname);
            viewHolder2.goodsPrice = convertView.findViewById(R.id.tv_goodsprice);
            viewHolder2.ivPic = convertView.findViewById(R.id.imageV_aa);
            viewHolder2.addAndSubView = convertView.findViewById(R.id.addandsub);
            convertView.setTag(viewHolder2);
        } else {
            viewHolder2 = (ViewHolder2) convertView.getTag();
        }
        final ShopCartBean.DataBean.ListBean listBean = dataBeans.get(groupPosition).getList().get(childPosition);
        viewHolder2.goodsName.setText(listBean.getTitle());
        viewHolder2.goodsPrice.setText(listBean.getPrice() + "");
        viewHolder2.ccche.setChecked(listBean.isChecked());
        viewHolder2.addAndSubView.setCurrentNum(listBean.getNum());
        String images = listBean.getImages();
        String[] split = images.split("\\|");
        Picasso.with(context).load(split[0]).into(viewHolder2.ivPic);

        viewHolder2.addAndSubView.setOnNumChangedListener(new AddAndSubView.OnNumChangedListener() {
            @Override
            public void onNumChanged(View v, int currentNum) {
                listBean.setNum(currentNum);
                if (listener != null) {
                    listener.onNumChanged(v, currentNum);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder1 {
        CheckBox checkBox;
        TextView textView;
    }

    class ViewHolder2 {
        CheckBox ccche;
        TextView goodsName, goodsPrice;
        ImageView ivPic;
        AddAndSubView addAndSubView;
    }

    public void setOnNumChangedListener(AddAndSubView.OnNumChangedListener listener) {
        this.listener = listener;

    }
}
