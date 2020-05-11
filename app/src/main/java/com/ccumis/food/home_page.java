package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class home_page extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button version;




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

        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_24px));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_explore_24px));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34,151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
