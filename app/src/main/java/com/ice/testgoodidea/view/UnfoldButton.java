package com.ice.testgoodidea.view;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by asd on 2/20/2017.
 */

public class UnfoldButton extends FloatingActionButton {

    private Context mContext;
    private ViewGroup mRootView;
    private FrameLayout mBackground;

    public UnfoldButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                addElement();
                Toast.makeText(context, "电解铝", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addElement() {
        Button b = new Button(mContext);
        b.setText("哎哟 可以加进来哟");
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(lp);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.removeView(mBackground);
            }
        });
        mBackground.addView(b);

    }


    private void init( ){

        mRootView = (ViewGroup) getParent();
        mBackground = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                mRootView.getWidth(),
                mRootView.getHeight());
        mBackground.setLayoutParams(params);
        mBackground.setBackgroundColor(Color.TRANSPARENT);
        mBackground.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.removeView(mBackground);
            }
        });

        mRootView.addView(mBackground);

    }

}
