package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccumis.food.Model.User;
import com.ccumis.food.fragment.Page4;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class personnelInfo extends AppCompatActivity {
private View v;
private Button back;
private Button update;
     EditText edit1;
     EditText edit2;
     EditText edit3 ;
     EditText edit4;
     EditText edit5;
     EditText edit6;
     String doc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);

        edit1 = (EditText)findViewById(R.id.nickname);
        edit2 = (EditText)findViewById(R.id.oripwd);
        edit3 = (EditText)findViewById(R.id.newpwd);
        edit4 = (EditText)findViewById(R.id.conpwd);
        edit5 = (EditText)findViewById(R.id.raddr);
        edit6 = (EditText)findViewById(R.id.tel);
        getInfo();

    }
    public void getInfo(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("User").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                        User user = new User(task.getResult().getString("nickN"), task.getResult().getString("mail"), task.getResult().getString("phone"), task.getResult().getString("address"), task.getResult().getString("userId"));
                        if(user.getUId().equals(firebaseUser.getUid())){
                            edit1.setText(user.getNickN());
                            edit5.setText(user.getAddress());
                            edit6.setText(user.getPhone());
                        }
                }
            }
        });
    }
    public void updateInfo(View view){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
        df.update("nickN",edit1.getText().toString());
        df.update("address",edit5.getText().toString());
        df.update("phone",edit6.getText().toString());


        if(!edit3.getText().toString().equals(edit4.getText().toString())){
            edit4.setError("密碼不符合，請再輸入一次");
        }
        firebaseUser.updatePassword(edit3.getText().toString()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(personnelInfo.this,"Failure to update!",Toast.LENGTH_LONG).show();
            }
        });

        finish();

    }

    public void goBack(View view){
        finish();
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
