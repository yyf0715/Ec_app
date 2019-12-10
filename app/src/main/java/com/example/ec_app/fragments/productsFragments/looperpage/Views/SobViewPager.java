package com.example.ec_app.fragments.productsFragments.looperpage.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SobViewPager extends ViewPager {
    private static final String TAG = "SobViewPager";
    private OnPagerItemClickListener mItemClickListener = null;
    private long delayTime = 3000;

    public SobViewPager(@NonNull Context context) {
        this(context, null);
    }

    private boolean isClick = false;
    private float downX;
    private float downY;
    private long downTime;
    public SobViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        downTime = System.currentTimeMillis();
                        stopLooper();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        float dx = Math.abs(event.getX() - downX);
                        float dy = Math.abs(event.getY() - downY);
                        long dTime = System.currentTimeMillis() - downTime;
                        isClick = dy <= 5 && dx <= 5 && dTime <= 1000;
                        Log.d(TAG, "is Click -- > " + isClick);
                        if (isClick && mItemClickListener != null) {
                            mItemClickListener.onItemClick(getCurrentItem());
                        }
                        startLooper();
                        break;
                }return false;
            }
        });

    }

    private void startLooper() {
        removeCallbacks(mTask);
        postDelayed(mTask,delayTime);
    }

    private void stopLooper() {
        removeCallbacks(mTask);
    }


    public void setPagerItemClickListener(OnPagerItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnPagerItemClickListener {
        void onItemClick(int position);
    }



    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            postDelayed(this,delayTime);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow....");
        startLooper();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow...");
        stopLooper();
    }
}
