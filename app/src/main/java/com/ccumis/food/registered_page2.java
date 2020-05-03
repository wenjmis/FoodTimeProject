package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registered_page2 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth  = FirebaseAuth.getInstance();
    private Map<String,Object> data;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page2);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("親愛的用戶您好：\n"+"      "+getResources().getString(R.string.notice));

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }



        bundle = this.getIntent().getExtras();
        data = new HashMap<>();
        data.put("type",bundle.getInt("type"));
        data.put("realN",bundle.getString("realN"));
        data.put("nickN",bundle.getString("nickN"));
        data.put("account",bundle.getString("account"));
        data.put("mail",bundle.getString("mail"));
        data.put("pwd",bundle.getString("pwd"));
        data.put("phone",bundle.getString("phone"));
        data.put("address",bundle.getString("address"));
    }
    public void done(View view){
        CheckBox checkBox = findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            Intent intent = new Intent(this,MainActivity.class);
            db.collection("User")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            startActivity(intent);
            registered_page2.this.finish();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(registered_page2.this);

            builder.setTitle("錯誤");
            builder.setMessage("NMSL 看完還敢不打勾啊");
            builder.setCancelable(true);
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    @Override
    public void onBackPressed(){
        registered_page2.this.finish();
    }
    public void registered(View view){

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        CheckBox checkBox = findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            fAuth.createUserWithEmailAndPassword(bundle.getString("mail"),bundle.getString("pwd")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(registered_page2.this, "註冊成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(registered_page2.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(registered_page2.this);

            builder.setTitle("錯誤");
            builder.setMessage("NMSL 看完還敢不打勾啊");
            builder.setCancelable(true);
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}
