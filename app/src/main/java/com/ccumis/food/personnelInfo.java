package com.ccumis.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ccumis.food.Model.User;
import com.ccumis.food.fragment.Page4;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class personnelInfo extends AppCompatActivity {
     EditText nickname;
     EditText oripwd;
     EditText newpwd;
     EditText conpwd;
     EditText address;
     EditText tel;
     TextView realName;
     Switch change;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);

        change = (Switch)findViewById(R.id.pwdChg) ;
        realName = (TextView)findViewById(R.id.textView11) ;
        nickname = (EditText)findViewById(R.id.nickname);
        oripwd = (EditText)findViewById(R.id.oripwd);
        newpwd = (EditText)findViewById(R.id.newpwd);
        conpwd = (EditText)findViewById(R.id.conpwd);
        address = (EditText)findViewById(R.id.address);
        tel = (EditText)findViewById(R.id.tel);
        getInfo();
        change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!change.isChecked()){
                    oripwd.setEnabled(false);
                    newpwd.setEnabled(false);
                    conpwd.setEnabled(false);
                }
                else {
                    oripwd.setEnabled(true);
                    newpwd.setEnabled(true);
                    conpwd.setEnabled(true);
                }
            }


        });
    }



    public void getInfo(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("User").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                        User user = new User(task.getResult().getString("realN"),task.getResult().getString("nickN"), task.getResult().getString("mail"), task.getResult().getString("phone"), task.getResult().getString("address"), task.getResult().getString("userId"));
                        if(user.getUId().equals(firebaseUser.getUid())){
                            realName.setText(user.getRealN());
                            nickname.setText(user.getNickN());
                            address.setText(user.getAddress());
                            tel.setText(user.getPhone());
                        }
                }

            }
        });

    }


    public void updateInfo(View view) {
        if(!change.isChecked()){
            //判斷空值
            if(nickname.getText().toString().isEmpty()){
                nickname.setError("不能為空值");
            }
            else if(address.getText().toString().isEmpty()){
                address.setError("不能為空值");
            }
            else if(tel.getText().toString().isEmpty()){
                tel.setError("不能為空值");
            }
            else
            {
                submit_to_firestore();
                finish();
            }
        }
        else {
            //判斷空值
            if(nickname.getText().toString().isEmpty()){
                nickname.setError("不能為空值");
            }
            else if(address.getText().toString().isEmpty()){
                address.setError("不能為空值");
            }
            else if(tel.getText().toString().isEmpty()){
                tel.setError("不能為空值");
            }
            else if(oripwd.getText().toString().isEmpty()){
                oripwd.setError("不能為空值");
            }
            else if(newpwd.getText().toString().isEmpty()){
                newpwd.setError("不能為空值");
            }
            else if(conpwd.getText().toString().isEmpty()){
                conpwd.setError("不能為空值");
            }
            else{
                //新密碼重複驗證
                if(!conpwd.getText().toString().equals(newpwd.getText().toString())){
                    conpwd.setError("與新密碼不符");
                }
                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oripwd.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(personnelInfo.this,"原密碼正確",Toast.LENGTH_LONG);
                                        change_passward();
                                        submit_to_firestore();
                                        finish();
                                    }
                                    else {
                                        Log.d("錯誤","錯誤碼為"+task.getException().getMessage());
                                        oripwd.setError("原密碼錯誤");
                                    }
                                }
                            });

                }
            }
        }
    }

    private void change_passward() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updatePassword(newpwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(personnelInfo.this,"修改成功",Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(personnelInfo.this,task.getException().getMessage(),Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void submit_to_firestore() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
        df.update("nickN", nickname.getText().toString());
        df.update("address", address.getText().toString());
        df.update("phone", tel.getText().toString());
        Toast.makeText(personnelInfo.this,"修改成功",Toast.LENGTH_LONG);
    }


    public void goBack(View view){
        finish();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

}
