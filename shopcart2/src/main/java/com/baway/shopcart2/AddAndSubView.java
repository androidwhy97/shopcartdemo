package com.baway.shopcart2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/22
 */
public class AddAndSubView extends LinearLayout {

    private View rootview;
    private TextView textAdd, textNum, textSub;

    public AddAndSubView(Context context) {
        this(context, null);
    }

    public AddAndSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AddAndSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initListener() {
        textAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textNum.getText().toString().trim();
                int parseInt = Integer.parseInt(trim);
                parseInt++;
                setCurrentNum(parseInt);
            }
        });

        textSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textNum.getText().toString().trim();
                int parseInt = Integer.parseInt(trim);
                parseInt--;
                if (parseInt > 0) {
                    setCurrentNum(parseInt);
                } else {
                    Toast.makeText(getContext(), "不能再少了亲", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(Context context) {
        rootview = View.inflate(context, R.layout.layout_add_sub, this);
        textAdd = rootview.findViewById(R.id.txt_add);
        textNum = rootview.findViewById(R.id.txt_num);
        textSub = rootview.findViewById(R.id.txt_sub);
        textNum.setText(1 + "");
    }

    public void setCurrentNum(int curNum) {
        textNum.setText(curNum + "");
        if (listener != null) {
            listener.onNumChanged(this, curNum);
        }
    }

    public interface OnNumChangedListener {
        void onNumChanged(View v, int currentNum);
    }

    private OnNumChangedListener listener;

    public void setOnNumChangedListener(OnNumChangedListener listener) {
        this.listener = listener;
    }
}
