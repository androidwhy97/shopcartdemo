package com.baway.shopcartdemo;

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
 * 创建时间：2018/11/21
 */
public class AddOrSubView extends LinearLayout {

    private View rootView;
    private TextView add, sub, number;


    public AddOrSubView(Context context) {
        this(context, null);
    }

    public AddOrSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AddOrSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initListener() {
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sub();
            }
        });
    }

    private void add() {
        String s = number.getText().toString().trim();
        int parseInt = Integer.parseInt(s);
        parseInt++;
        setCurrentcount(parseInt);
    }

    private void sub() {
        String s = number.getText().toString().trim();
        int parseInt = Integer.parseInt(s);
        if (parseInt > 1) {
            parseInt--;
            setCurrentcount(parseInt);
        } else {
            Toast.makeText(getContext(), "不能再少了亲", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(Context context) {
        rootView = View.inflate(context, R.layout.layout_add_sub, this);
        add = rootView.findViewById(R.id.text_add);
        sub = rootView.findViewById(R.id.text_sub);
        number = rootView.findViewById(R.id.text_num);
        number.setText(1 + "");
    }

    public interface onNumChangedListener {
        void onNumChanged(View view, int curnum);
    }

    private onNumChangedListener listener;

    public void setonNumChangedListener(onNumChangedListener listener) {
        this.listener = listener;
    }

    public void setCurrentcount(int curnum) {
        number.setText(curnum+"");
        if (listener != null) {
            listener.onNumChanged(this, curnum);
        }
    }
}
