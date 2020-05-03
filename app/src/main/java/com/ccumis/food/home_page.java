package com.ccumis.food;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {


    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] strings = new String[4];
    final int NUM_ITEMS = strings.length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        strings[0]=getResources().getString(R.string.tab1);
        strings[1]=getResources().getString(R.string.tab2);
        strings[2]=getResources().getString(R.string.tab3);
        strings[3]=getResources().getString(R.string.tab4);
        fragmentList.add(new Page1());
        fragmentList.add(new Page2());
        fragmentList.add(new Page3());
        fragmentList.add(new Page4());
        initView();
    }

    private void initView() {
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyAdapter fragmentAdapter = new  MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tab_layout.setupWithViewPager(viewPager);
        tab_layout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_24px));
        tab_layout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_explore_24px));
        tab_layout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_forum_24px));
        tab_layout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_settings_applications_24px));

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity( new Intent(this,MainActivity.class));
        finish();
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
