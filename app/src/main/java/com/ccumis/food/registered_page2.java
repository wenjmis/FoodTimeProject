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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registered_page2 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth  = FirebaseAuth.getInstance();
    private Map<String,Object> data;
    private Map<String,Object> shopdata;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page2);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("親愛的用戶您好：\n"+"      "+getResources().getString(R.string.notice));



        bundle = this.getIntent().getExtras();
        data = new HashMap<>();
        shopdata =new HashMap<>();
        data.put("type",bundle.getInt("type"));
        data.put("realN",bundle.getString("realN"));
        data.put("nickN",bundle.getString("nickN"));
        data.put("GSP",bundle.getString("GSP"));
        data.put("mail",bundle.getString("mail"));
        data.put("phone",bundle.getString("phone"));
        data.put("address",bundle.getString("address"));
        shopdata.put("address",bundle.getString("comp_address_text"));
        shopdata.put("name",bundle.getString("comp_name_text"));
        shopdata.put("phone",bundle.getString("comp_phone_text"));

    }

    @Override
    public void onBackPressed(){
        registered_page2.this.finish();
    }
    public void registered(View view){

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        CheckBox checkBox = findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            fAuth.createUserWithEmailAndPassword(bundle.getString("mail"),bundle.getString("pwd")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(registered_page2.this, "註冊成功", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = fAuth.getCurrentUser();
                        data.put("userId",user.getUid());
                        if(bundle.getInt("type")==0){
                            db.collection("User").document(user.getUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(registered_page2.this, "成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            registered_page2.this.finish();
                        }
                        else if(bundle.getInt("type")==1){
                            db.collection("User").document(user.getUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(registered_page2.this, "成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            db.collection("Shop").document(user.getUid()).set(shopdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(registered_page2.this, "成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            registered_page2.this.finish();
                        }

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
            builder.setMessage("請詳閱聲明後並於左下角打勾再送出資料");
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
