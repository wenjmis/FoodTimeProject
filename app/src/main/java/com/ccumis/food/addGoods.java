package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
    private TimePickerView pvTime;


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

        initTimePicker();

    }

    private void initTimePicker() {
        final EditText last_time = findViewById(R.id.last_time);
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                last_time.setText(getTime(date));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {

                    }
                })
                .setType(new boolean[]{false, true, true, true, true, false})
                .setContentTextSize(24)
                .isDialog(true)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改動畫樣式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部顯示
            }
        }
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(date);
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
        database.child("commodity").child(good_name.getText().toString()).setValue(hashMap);
        Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT);
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public void picktime(View view) {
        if (pvTime != null) {
            pvTime.show(view);//彈出時間選擇器，傳遞引數過去，回撥的時候則可以繫結此view
        }
    }
}
