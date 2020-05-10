package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class addGoods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout(width * 1, (int) (height * .9));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Switch my_switch = findViewById(R.id.chip2);
        final EditText editText = findViewById(R.id.price);
        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (my_switch.isChecked()) {
                    editText.setEnabled(true);
                } else
                    editText.setEnabled(false);
            }
        });


    }

    public void submit_goods(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("commodity");

        Locations locations = new Locations();
        EditText good_distribution=findViewById(R.id.good_distribution);
        EditText good_name=findViewById(R.id.good_name);
        EditText good_price=findViewById(R.id.price);
        EditText last_time=findViewById(R.id.last_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日HH:mm");
        Date curDate = new Date(System.currentTimeMillis());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("address",locations.locationlist);
        hashMap.put("good_distribution",good_distribution.getText().toString());
        hashMap.put("good_name",good_name.getText().toString());
        hashMap.put("good_price",good_price.getText().toString());
        hashMap.put("last_time",last_time.getText().toString());
        hashMap.put("start_time",simpleDateFormat.format(curDate));
        hashMap.put("telephone","");
        hashMap.put("user_id",user.getUid());
        database.setValue(hashMap);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    class Locations implements LocationListener {
        String locationlist;
        @Override
        public void onLocationChanged(Location location) {
            locationlist = location.getLatitude()+","+location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
