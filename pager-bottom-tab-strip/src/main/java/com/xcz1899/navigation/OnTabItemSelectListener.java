package com.xcz1899.navigation;

/**
 * 导航栏按钮点击监听
 */
public interface OnTabItemSelectListener
{
    /**
     * 选中导航栏的某一项
     * @param index 索引导航按钮，按添加顺序排序
     */
    void onSelected(int index);


}
