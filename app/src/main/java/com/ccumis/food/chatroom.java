package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.Iterator;

public class chatroom extends AppCompatActivity {

    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        /*
        String room_name = getIntent().getExtras().get("room name").toString();
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.message_display);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(room_name);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_Chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_Chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_Chat(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser User=  FirebaseAuth.getInstance().getCurrentUser();
                String name = User.getEmail();
                String message = editText.getText().toString();
                reference.child("msg").setValue(message);
                reference.child("sender").setValue(name);
            }
        });*/
    }

    public void append_Chat(DataSnapshot dataSnapshot) {

        /*String name = new String();
        String room_name = new String();
        Iterator i =dataSnapshot.getChildren().iterator();
        while (i.hasNext()){
            name = ((DataSnapshot) i.next()).getValue().toString();
            room_name=((DataSnapshot) i.next()).getValue().toString();
        }

        textView.append(name +":" +room_name +"\n" );*/
    }
}
