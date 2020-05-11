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
private View v;
private CheckBox checkBox;
private Button back;
private Button update;
     EditText edit1;
     EditText edit2;
     EditText edit3 ;
     EditText edit4;
     EditText edit5;
     EditText edit6;
     TextView realName;
     Switch change;
     String input1;
     String input2;
     String input3;
     String input4;
     String input5;
     String input6;



     String doc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);

        change = (Switch)findViewById(R.id.pwdChg) ;
        realName = (TextView)findViewById(R.id.textView11) ;
        edit1 = (EditText)findViewById(R.id.nickname);
        edit2 = (EditText)findViewById(R.id.oripwd);
        edit3 = (EditText)findViewById(R.id.newpwd);
        edit4 = (EditText)findViewById(R.id.conpwd);
        edit5 = (EditText)findViewById(R.id.raddr);
        edit6 = (EditText)findViewById(R.id.tel);
        update = (Button)findViewById(R.id.button7) ;
        back = (Button)findViewById(R.id.button4);
        checkBox = (CheckBox)findViewById(R.id.checkBox3);






        getInfo();
        change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);



                }
                if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);


                }



            }


        });
        writeInfo();

        edit2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit3.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit4.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                    edit2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edit3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edit4.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edit2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edit3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edit4.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                            edit1.setText(user.getNickN());
                            edit5.setText(user.getAddress());
                            edit6.setText(user.getPhone());
                        }
                }

            }
        });

    }


    public void updateInfo(View view){
        final EditText[]each ={edit1,edit2,edit3,edit4,edit5,edit6};
        final EditText[] each1 = {edit1,edit5,edit6};

        if(change.isChecked()) {
            edit2.setEnabled(false);
            edit3.setEnabled(false);
            edit4.setEnabled(false);
            if(edit1.getText().toString().isEmpty()||edit5.getText().toString().isEmpty()||edit6.getText().toString().isEmpty()){
                for (int i = 0; i < each1.length; i++) {
                    EditText currentEdit1 = each1[i];
                    if (currentEdit1.getText().toString().isEmpty()) {
                        each1[i].setError("不能有空白欄位");
                    }
                }
            }

            else if(!edit1.getText().toString().isEmpty()&&!edit5.getText().toString().isEmpty()&&!edit6.getText().toString().isEmpty()){

                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
                df.update("nickN",edit1.getText().toString());
                df.update("address",edit5.getText().toString());
                df.update("phone",edit6.getText().toString());
                finish();
            }
        }
        else if(!change.isChecked()){
            edit2.setEnabled(true);
            edit3.setEnabled(true);
            edit4.setEnabled(true);
            if(edit1.getText().toString().isEmpty()||edit2.getText().toString().isEmpty()||edit3.getText().toString().isEmpty()||
                    edit4.getText().toString().isEmpty()||edit5.getText().toString().isEmpty()||edit6.getText().toString().isEmpty()){
                for(int i=0;i<each.length;i++){
                    EditText currentEdit = each[i];
                    if(currentEdit.getText().toString().isEmpty()){
                        each[i].setError("不能有空白欄位");
                    }
                }
            }
             else if(!edit1.getText().toString().isEmpty()&&!edit2.getText().toString().isEmpty()||!edit3.getText().toString().isEmpty()
                     ||!edit4.getText().toString().isEmpty()&&!edit5.getText().toString().isEmpty()&&!edit6.getText().toString().isEmpty()){
                 if(!edit3.getText().toString().equals(edit4.getText().toString())){
                     edit4.setError("密碼不符合，請再輸入一次");
                 }
                 else{
                     final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                     FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                     DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
                     df.update("nickN",edit1.getText().toString());
                     df.update("address",edit5.getText().toString());
                     df.update("phone",edit6.getText().toString());
                     finish();
                     firebaseUser.updatePassword(edit3.getText().toString()).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(personnelInfo.this,"更新失敗，請稍後再試!",Toast.LENGTH_LONG).show();
                         }
                     });
                 }
             }
         }


        /*else{
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
            df.update("nickN",edit1.getText().toString());
            df.update("address",edit5.getText().toString());
            df.update("phone",edit6.getText().toString());
            finish();
        }*/
        change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });





    /*   final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();*/


       /* if(!edit3.getText().toString().equals(edit4.getText().toString())){
            edit4.setError("密碼不符合，請再輸入一次");

        }*/
      /*  DocumentReference df = firestore.collection("User").document(firebaseUser.getUid());
        df.update("nickN",edit1.getText().toString());
        df.update("address",edit5.getText().toString());
        df.update("phone",edit6.getText().toString());*/



       /* firebaseUser.updatePassword(edit3.getText().toString()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(personnelInfo.this,"更新失敗，請稍後再試!",Toast.LENGTH_LONG).show();
            }
        });*/



         /*finish();*/


    }




    public void goBack(View view){
        finish();
    }


    public void onBackPressed(View view) {

        finish();
    }

    public void writeInfo(){

        final EditText[] each2 = {edit2,edit3,edit4};
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);



                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(edit1.getText().toString().isEmpty()){
                        edit1.setError("不能有空白欄位");
                    }if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit1.getText().toString().isEmpty()){
                        edit1.setError("不能有空白欄位");
                    }if(change.isChecked()){
                        edit2.setEnabled(false);
                        edit3.setEnabled(false);
                        edit4.setEnabled(false);
                    }


                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);



                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);



                }

            }
        });
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);




                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }



                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit2.getText().toString().isEmpty()){
                        edit2.setError("不能有空白欄位");
                    }
                    if(change.isChecked()){
                        edit2.setEnabled(false);
                        edit3.setEnabled(false);
                        edit4.setEnabled(false);

                    }


                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);




                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit2.getText().toString().equals(edit3.getText().toString())){
                        edit2.setError("原密碼錯誤");
                    }

                }
            }
        });
        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit3.getText().toString().isEmpty()){
                        edit3.setError("不能有空欄位");
                    }


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }



                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit3.getText().toString().isEmpty()){
                        edit3.setError("不能有空白欄位");
                    }
                    if(edit3.getText().toString().equals(edit2.getText().toString())){
                        edit3.setError("新密碼不能是原密碼");
                    }if(change.isChecked()){
                        edit2.setEnabled(false);
                        edit3.setEnabled(false);
                        edit4.setEnabled(false);
                    }




                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);




                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit3.getText().toString().equals(edit2.getText().toString())){
                        edit3.setError("新密碼不能是原來密碼");
                    }


                }

            }
        });
        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);





                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);



                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }




                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(edit4.getText().toString().isEmpty()){
                        edit4.setError("不能有空白欄位");
                    }if(change.isChecked()){
                        edit2.setEnabled(false);
                        edit3.setEnabled(false);
                        edit4.setEnabled(false);
                    }


                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);





                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(!edit4.getText().toString().equals(edit3.getText().toString())){
                        edit4.setError("確認密碼不符");
                    }
                    if(edit4.getText().toString().equals(edit2.getText().toString())){
                        edit4.setError("確認密碼不能是原密碼");
                    }


                }

            }
        });
        edit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(edit5.getText().toString().isEmpty()){
                        edit5.setError("不能有空白欄位");
                    }if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }


                }
               else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                }if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);






                }
               else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);


                }

            }
        });
        edit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);


                }
               else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);


                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    edit2.setError(null);
                    edit3.setError(null);
                    edit4.setError(null);
                    if(edit6.getText().toString().isEmpty()){
                        edit6.setError("不能有空白欄位");
                    }if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(change.isChecked()){
                        edit2.setEnabled(false);
                        edit3.setEnabled(false);
                        edit4.setEnabled(false);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(change.isChecked()){
                    edit2.setEnabled(false);
                    edit3.setEnabled(false);
                    edit4.setEnabled(false);
                    if(!change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }


                }
                else if(!change.isChecked()){
                    edit2.setEnabled(true);
                    edit3.setEnabled(true);
                    edit4.setEnabled(true);
                    if(change.isChecked()){
                        edit2.setEnabled(true);
                        edit3.setEnabled(true);
                        edit4.setEnabled(true);
                    }

                }

            }
        });
    }
}
