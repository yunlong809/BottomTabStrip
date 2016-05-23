package com.xcz1899.pagerbottomtabstriptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.xcz1899.navigation.OnTabItemSelectListener;
import com.xcz1899.navigation.PagerBottomTabStrip;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    int[] testColors = {0xFF00796B, 0xFF8D6E63, 0xFF2196F3, 0xFF607D8B, 0xFFF57C00};

    /**
     * 未选中状态下的图标和文字颜色
     */
    private int mColorDefault = 0x89000000;
    List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这里这样使用Fragment仅用于测试，请勿模仿！
        initFragment();
        BottomTabTest();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(createFragment("A"));
        mFragments.add(createFragment("B"));
        mFragments.add(createFragment("C"));
        mFragments.add(createFragment("D"));
        mFragments.add(createFragment("E"));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
        transaction.add(R.id.frameLayout, mFragments.get(0));
        transaction.commit();
    }

    private void BottomTabTest() {
        //用TabItemBuilder构建一个导航按钮
        PagerBottomTabStrip pagerTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);
        if (pagerTabStrip != null) {
            pagerTabStrip.addItem(true, android.R.drawable.ic_menu_compass, android.R.drawable.ic_menu_compass, "位置", testColors[0], mColorDefault)
                    .addItem(false, android.R.drawable.ic_menu_search, android.R.drawable.ic_menu_search, "搜索", testColors[1], mColorDefault)
                    .addItem(false, android.R.drawable.ic_menu_help, android.R.drawable.ic_menu_help, "帮助", testColors[2], mColorDefault)
                    .addItem(false, android.R.drawable.ic_menu_send, android.R.drawable.ic_menu_send, "信息", testColors[3], mColorDefault)
                    .build();
            pagerTabStrip.setmOnTabItemClickListener(listener);
        }
    }


    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
            transaction.replace(R.id.frameLayout, mFragments.get(index));
            transaction.commit();
        }
    };

    private Fragment createFragment(String content) {
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        fragment.setArguments(bundle);

        return fragment;
    }

}
