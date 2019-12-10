package com.example.ec_app.fragments.productsFragments.looperpage.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ec_app.R;

public class SobLooperPager extends LinearLayout {
    private SobViewPager mViewPager;
    private LinearLayout mPointContainer;
    private TextView mTitleTv;
    private BindTitleListener mTitleSetListener = null;
    private InnerAdapter mInnerAdapter = null;
    private OnItemClickListener mOnItemClickListener = null;

    public SobLooperPager(Context context) {
        this(context, null);
    }

    public SobLooperPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SobLooperPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.looper_pager_layout, this, true);

        init();
    }

    private void init() {
        initView();
        initEvent();

    }

    private void initEvent() {
        mViewPager.setPagerItemClickListener(new SobViewPager.OnPagerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(mOnItemClickListener != null && mInnerAdapter != null){
                    int realPosition = position % mInnerAdapter.getDataSize();
                    mOnItemClickListener.onItemClick(realPosition);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //切换的一个回调方法
            }

            @Override
            public void onPageSelected(int position) {
                //切换停下来的回调
                int realPosition = position % mInnerAdapter.getDataSize();
                if (mTitleSetListener != null){
                    mTitleTv.setText(mTitleSetListener.getTitle(realPosition));
                }
                updateIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //切换状态改变的回调
            }
        });
    }

    private void initView() {
        mViewPager = this.findViewById(R.id.looper_pager_vp);
        mPointContainer = this.findViewById(R.id.looper_point_container_lv);
        mTitleTv = this.findViewById(R.id.looper_title_tv);
        mViewPager.setOffscreenPageLimit(3);


    }

    public void setData(InnerAdapter innerAdapter, BindTitleListener listener) {
        this.mInnerAdapter = innerAdapter;
        this.mTitleSetListener = listener;
        mViewPager.setAdapter(innerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 );
        if (listener != null) {
            mTitleTv.setText(listener.getTitle(mViewPager.getCurrentItem() % mInnerAdapter.getDataSize()));
        }
        updateIndicator();//设置图片相同数量的圆点
    }

    private void updateIndicator() {
        if(mInnerAdapter != null && mTitleSetListener != null){
            int count = mInnerAdapter.getDataSize();
            mPointContainer.removeAllViews();//先移除，否者会出错
            for (int i = 0; i < count; i++) {
                View point = new View(getContext());
                if (mViewPager.getCurrentItem() % count ==i){//当前显示的图片
                    point.setBackgroundResource(R.drawable.shape_circle_read);
                }else{
                    point.setBackgroundResource(R.drawable.shape_circle_white);
                }
                LayoutParams params = new LayoutParams(
                        SizeUtils.dip2px(getContext(),5),
                        SizeUtils.dip2px(getContext(),5));
                params.setMargins(SizeUtils.dip2px(getContext(),5),0,SizeUtils.dip2px(getContext(),5),0);
                point.setLayoutParams(params);
                mPointContainer.addView(point);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface BindTitleListener {
        String getTitle(int position);
    }

    public abstract static class InnerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final int realPosition = position % getDataSize();//真实位置
            View itemView = getSubView(container, realPosition);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        protected abstract int getDataSize();//从外界获取数据

        protected abstract View getSubView(ViewGroup container, int position);//外界获取图片资源
    }

}
