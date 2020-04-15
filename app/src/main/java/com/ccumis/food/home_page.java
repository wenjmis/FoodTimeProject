package com.ccumis.food;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {

    static final int NUM_ITEMS = 2;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{"A","B"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fragmentList.add(new Page1());
        fragmentList.add(new Page2());
        initView();
    }

    private void initView() {
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setTabMode(1);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyAdapter fragmentAdapter = new  MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tab_layout.setupWithViewPager(viewPager);

    }
    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);

        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
