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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class chatroom extends AppCompatActivity {
    MessageAdapter messageAdapter;
    List<Message> messages;
    RecyclerView recyclerView;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Intent intent = getIntent();

        recyclerView=findViewById(R.id.display_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);

        final String reveiver_id = intent.getExtras().getString("receiver_id");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = findViewById(R.id.username);
        textView.setText(reveiver_id);

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
                    databaseReference.child("Message").push().setValue(hashMap);
                    editText.setText("");


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
                        Room room = new Room(snapshot.getString("menber_1"),snapshot.getString("menber_2"));
                        if((room.getMenber_1().equals(firebaseUser) && room.getMenber_2().equals(reveiver_id))){

                        }
                        else if((room.getMenber_1().equals(reveiver_id) && room.getMenber_2().equals(firebaseUser))){

                        }
                        else {
                            Map<String ,Object> hashMap = new HashMap<>();
                            hashMap.put("menber_1",firebaseUser);
                            hashMap.put("menber_2",reveiver_id);
                            firebaseFirestore.collection("Room").add(hashMap);
                            hashMap.clear();
                            hashMap.put("menber_2",firebaseUser);
                            hashMap.put("menber_1",reveiver_id);
                            firebaseFirestore.collection("Room").add(hashMap);
                        }
                    }
                }
            }
        });


    }


    public void readMessage(final String myid, final String userid, final String imageurl){
        messages = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Message");
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
