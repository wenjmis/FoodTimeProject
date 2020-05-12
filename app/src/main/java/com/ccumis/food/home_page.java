package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ccumis.food.fragment.Page1;
import com.ccumis.food.fragment.Page2;
import com.ccumis.food.fragment.Page3;
import com.ccumis.food.fragment.Page4;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Page1(),getResources().getString(R.string.tab1));
        viewPagerAdapter.addFragment(new Page2(),getResources().getString(R.string.tab2));
        viewPagerAdapter.addFragment(new Page3(),getResources().getString(R.string.tab3));
        viewPagerAdapter.addFragment(new Page4(),getResources().getString(R.string.tab4));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        final TextView appbar_title = findViewById(R.id.appbar_title);
        appbar_title.setText("找剩食");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        appbar_title.setText("找剩食");
                        break;
                    case 1:
                        appbar_title.setText("找附近供應者");
                        break;
                    case 2:
                        appbar_title.setText("預約及聯絡");
                        break;
                    case 3:
                        appbar_title.setText("個人設定");
                        break;
                    default:
                        appbar_title.setText("食刻");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_24px));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_place_black_24dp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_forum_24px));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_settings_applications_24px));

        //google_map
        // mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity( new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent=  new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void Addgoods(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(this,addGoods.class);
        Bundle bundle = new Bundle();
        bundle.putString("UserId",user.getUid());
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void ChangeInfo (View view){
        startActivity(new Intent(this,personnelInfo.class));

    }


    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
