package com.ccumis.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ccumis.food.Model.commodity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Goods_view extends AppCompatActivity {
    private String UID;
    String goodname="";
    TextView commodity_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_view);
        Button button = findViewById(R.id.button4);
        final TextView good_name =findViewById(R.id.good_name);
        final EditText last_time = findViewById(R.id.last_time_edit);
        final EditText username = findViewById(R.id.username_edit);
        final EditText price = findViewById(R.id.price_edit);
        final EditText good_distribution= findViewById(R.id.good_distribution_edit);
        final EditText address= findViewById(R.id.address_edit);
        commodity_key=findViewById(R.id.commodity_key);
        Intent intent = getIntent();
        Bundle get = intent.getExtras();

        final String receiver_id = get.getString("receiver_id");
        goodname = get.getString("good_name");


        good_name.setText(get.getString("good_name"));
        last_time.setText(get.getString("last_time"));
        price.setText(get.getString("good_price"));
        good_distribution.setText(get.getString("good_distribution"));
        address.setText(get.getString("address"));
        username.setText(get.getString("name"));
        UID=get.getString("receiver_id");

        if(UID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            Button complete = findViewById(R.id.complete);
            button.setVisibility(View.INVISIBLE);
            button.setEnabled(false);
            complete.setEnabled(true);
            complete.setVisibility(View.VISIBLE);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Message").child(goodname).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                });
                FirebaseFirestore reference= FirebaseFirestore.getInstance();
                reference.collection("Room").document(goodname).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                            Log.d("error",task.getException().getMessage());
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("commodity").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            commodity commodity = snapshot.getValue(com.ccumis.food.Model.commodity.class);
                            if(commodity.good_name.equals(goodname)){
                                commodity_key.setText(snapshot.getKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                FirebaseDatabase.getInstance().getReference().child("commodity").child(commodity_key.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                finish();
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent =  new Intent(Goods_view.this, chatroom.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiver_id",receiver_id);
                bundle.putString("name",username.getText().toString());
                bundle.putString("good_name",good_name.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
