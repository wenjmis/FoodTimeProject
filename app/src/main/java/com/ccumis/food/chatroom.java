package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccumis.food.Model.Message;
import com.ccumis.food.Model.Room;
import com.ccumis.food.Model.User;
import com.ccumis.food.adapter.MessageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import java.lang.ref.Reference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class chatroom extends AppCompatActivity {
    MessageAdapter messageAdapter;
    List<Message> messages;
    RecyclerView recyclerView;
    DatabaseReference reference;
    String good_name;
    TextView current_name;
    String target_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Intent intent = getIntent();
        current_name = findViewById(R.id.current_name);

        recyclerView=findViewById(R.id.display_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);

        final String reveiver_id = intent.getExtras().getString("receiver_id");
        good_name = intent.getExtras().getString("good_name");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = findViewById(R.id.username);
        target_name = intent.getExtras().getString("name");
        textView.setText(target_name);

        final EditText editText = findViewById(R.id.send_Text);

        ImageButton button = findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    HashMap<String , Object> hashMap = new HashMap<>();
                    hashMap.put("msg",editText.getText().toString());
                    hashMap.put("receiver_id",reveiver_id);
                    hashMap.put("sender_id",firebaseUser.getUid());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
                    hashMap.put("send_time",dateFormat.format(new Date()));
                    databaseReference.child("Message").child(good_name).push().setValue(hashMap);
                    editText.setText("");


                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("User").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            current_name.setText(task.getResult().getString("nickN"));
                        }
                    });

                    //附帶新增聊天室
                    createRoom(firebaseUser.getUid(),reveiver_id);
                }
            }
        });
        reference =FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readMessage(firebaseUser.getUid(),reveiver_id,"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRoom(final String firebaseUser,final String reveiver_id) {

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Room").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot : task.getResult()){
                        Room room = new Room(snapshot.getString("menber_1"),snapshot.getString("menber_2"),snapshot.getString("menber_1_name"),snapshot.getString("menber_2_name"),snapshot.getString("good_name"));
                        if((room.getMenber_1().equals(firebaseUser) && room.getMenber_2().equals(reveiver_id) && room.getGood_name().equals(good_name))){

                        }
                        else if((room.getMenber_1().equals(reveiver_id) && room.getMenber_2().equals(firebaseUser) && room.getGood_name().equals(good_name))){

                        }
                        else {
                            Map<String ,Object> hashMap = new HashMap<>();
                            hashMap.put("menber_1",firebaseUser);
                            hashMap.put("menber_1_name",current_name.getText().toString());
                            hashMap.put("menber_2",reveiver_id);
                            hashMap.put("menber_2_name",target_name);
                            hashMap.put("good_name",good_name);
                            firebaseFirestore.collection("Room").document(good_name).set(hashMap);
                        }
                    }
                }
            }
        });


    }


    public void readMessage(final String myid, final String userid, final String imageurl){
        messages = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Message").child(good_name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    if(message.receiver_id.equals(myid) && message.sender_id.equals(userid) ||
                            message.receiver_id.equals(userid)&& message.sender_id.equals(myid)){
                        messages.add(message);
                    }
                    messageAdapter = new MessageAdapter(chatroom.this,messages,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
