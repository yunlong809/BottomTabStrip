package com.xcz1899.navigation;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 底部导航栏按钮存放
 */
public class PagerBottomTabStrip extends LinearLayout {
    //按钮列表（Material Design 标准 3-5个）
    protected List<TabItem> mTabItems = new ArrayList<>();

    private Context mContext;

    //点击事件监听器
    public OnTabItemSelectListener mOnTabItemClickListener;

    // 用于记录按钮的宽度 单位px
    private int mTabItemWidth;

    // Material Design 标准 按钮宽度限制
    private final int DEFAULT_MAX_WIDTH = 168;

    // 记录当前选中项
    public int mIndex = 0;


    public PagerBottomTabStrip(Context context) {
        super(context);
        init(context);
    }

    public PagerBottomTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PagerBottomTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setOrientation(HORIZONTAL);//设置水平排列
    }

    private boolean isCreated;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isCreated) {
            return;
        }

        //当导航按钮为0时不执行任何操作
        int ItemCount = mTabItems.size();
        if (ItemCount <= 0) {
            return;
        }
        //将导航栏宽度转换成DP,用于计算
        int width_dp = (int) Utils.px2dp(mContext, getMeasuredWidth());
        //根据按钮个数平分导航栏的长度
        int width_avg = width_dp / ItemCount;
        if (width_avg > DEFAULT_MAX_WIDTH) {
            int leftPadding = (int) Utils.dp2px(mContext, (width_dp - DEFAULT_MAX_WIDTH * ItemCount) / 2f);
            setPadding(leftPadding, 0, 0, 0);
            mTabItemWidth = (int) Utils.dp2px(mContext, DEFAULT_MAX_WIDTH);
        } else {
            mTabItemWidth = (int) Utils.dp2px(mContext, width_avg);
        }
        //给每个tabItem设置宽度
        for (TabItem item : mTabItems) {
            item.getLayoutParams().width = mTabItemWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        isCreated = true;
    }

    /**
     * 给导航栏添加tabItem
     *
     * @param isSelected    是否选中
     * @param defaultIcon   默认icon
     * @param selectedIcon  选中icon
     * @param text          文本
     * @param selectedColor 选中颜色
     * @param defaultColor  默认颜色
     */
    public PagerBottomTabStrip addItem(boolean isSelected, int defaultIcon, int selectedIcon, String text, int selectedColor, int defaultColor) {
        TabItem tabItem = new TabItem(mContext);
        tabItem.setSelected(isSelected);
        tabItem.setmText(text);
        tabItem.setmColorSelected(selectedColor);
        tabItem.setmColorDefault(defaultColor);
        tabItem.setmIconDefault(defaultIcon);
        tabItem.setmIconSelected(selectedIcon);
        addView(tabItem);
        mTabItems.add(tabItem);
        return this;
    }

    public void build(){
        setmOnTabItemClickListener();
    }


    /**
     * 设置点击事件
     *
     */
    private void setmOnTabItemClickListener() {
        for (int i = 0; i < mTabItems.size(); i++) {
            TabItem item = mTabItems.get(i);
            final int finalI = i;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(finalI);
                }
            });
        }
    }

    public void setmOnTabItemClickListener(OnTabItemSelectListener mOnTabItemClickListener) {
        this.mOnTabItemClickListener = mOnTabItemClickListener;
    }

    /**
     * 设置选中项
     *
     * @param index 顺序索引
     */
    private void setSelect(int index) {
        int itemCount = mTabItems.size();
        //点击已选中项 || 不应该存在的索引错误
        if (mIndex == index || index >= itemCount) {
            return;
        }

        mIndex = index;
        //设置tabItem 是否被选中
        for (int i = 0; i < itemCount; i++) {
            mTabItems.get(i).setSelected(i == index);
        }

        //外部回调
        if (mOnTabItemClickListener != null) {
            mOnTabItemClickListener.onSelected(index);
        }

    }


}
