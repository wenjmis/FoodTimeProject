package com.ccumis.food;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class addGoods extends AppCompatActivity {
    private String locations="";
    private Boolean business=false;
    TextView textView12;

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

        textView12 = findViewById(R.id.textView12);
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Switch my_switch = findViewById(R.id.chip2);
        final EditText editText = findViewById(R.id.price);


        FirebaseFirestore firestore =FirebaseFirestore.getInstance();
                firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                textView12.setText(task.getResult().getString("nickN"));
            }
        });


        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (my_switch.isChecked()) {
                    editText.setEnabled(true);
                } else
                    editText.setEnabled(false);
            }
        });
        Switch business_show = findViewById(R.id.switch1);
        business_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    business = true;
                    FirebaseFirestore.getInstance().collection("Shop").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            textView12.setText(task.getResult().getString("name"));
                        }
                    });
                }
                else {
                    business = false;
                    FirebaseFirestore firestore =FirebaseFirestore.getInstance();
                    firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            textView12.setText(task.getResult().getString("nickN"));
                        }
                    });
                }
            }
        });



    }

    public void submit_goods(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        EditText address = findViewById(R.id.address);
        EditText good_distribution=findViewById(R.id.good_distribution);
        EditText good_name=findViewById(R.id.good_name);
        EditText good_price=findViewById(R.id.price);
        EditText last_time=findViewById(R.id.last_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        /*FusedLocationProviderClient Client = LocationServices.getFusedLocationProviderClient(this);
        Client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                locations = location.getLatitude()+","+location.getLongitude();
            }
        });*/
        locations = address.getText().toString();

        final HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("address",locations);
        hashMap.put("good_distribution",good_distribution.getText().toString());
        hashMap.put("good_name",good_name.getText().toString());
        if(!good_price.getText().toString().isEmpty()){
            hashMap.put("good_price",good_price.getText().toString());
        }
        else {
            hashMap.put("good_price","0");
        }

        hashMap.put("last_time",last_time.getText().toString());
        hashMap.put("start_time",simpleDateFormat.format(curDate));
        hashMap.put("telephone","");
        hashMap.put("user_id",user.getUid());
        hashMap.put("name",textView12.getText().toString());
        database.child("commodity").push().setValue(hashMap);
        Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT);
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
