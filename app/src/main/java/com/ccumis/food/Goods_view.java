package com.ccumis.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ccumis.food.Model.commodity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Goods_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_view);
        Button button = findViewById(R.id.button4);
        final TextView good_name =findViewById(R.id.good_name);
        final TextView last_time = findViewById(R.id.last_time);
        final TextView username = findViewById(R.id.username);
        final TextView price = findViewById(R.id.price);
        final TextView good_distribution= findViewById(R.id.address);
        final TextView address= findViewById(R.id.address);




        Intent intent = getIntent();
        Bundle get = intent.getExtras();

        final String receiver_id = get.getString("receiver_id");
        final String goodname = get.getString("good_name");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent =  new Intent(Goods_view.this, chatroom.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiver_id",receiver_id);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("commodity");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    commodity commodity = snapshot.getValue(commodity.class);
                    if(commodity.good_name.equals(goodname)){
                        good_name.setText(commodity.good_name);
                        last_time.setText("剩餘時間:"+commodity.last_time);
                        username.setText("上傳者"+commodity.user_id);
                        price.setText("費用:"+commodity.good_price);
                        good_distribution.setText("簡介:\n"+commodity.good_distribution);
                        address.setText("地址:\n"+commodity.address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
