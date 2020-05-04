package com.ccumis.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class registered_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page);

    }
    public void go(View view){
        CheckBox checkBox = findViewById(R.id.checkBox2);
        if(checkBox.isChecked()) {
            Intent intent = new Intent(this, businregist_page.class);

            EditText editText = findViewById(R.id.editText3);
            EditText editText1 = findViewById(R.id.editText4);
            EditText editText2 = findViewById(R.id.editText5);
            EditText editText4 = findViewById(R.id.editText7);
            EditText editText5 = findViewById(R.id.editText8);
            EditText editText6 = findViewById(R.id.editText9);

            String realN = editText.getText().toString();
            String nickN = editText1.getText().toString();
            String mail = editText2.getText().toString();
            String pwd = editText4.getText().toString();
            String phone = editText5.getText().toString();
            String address = editText6.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("realN", realN);
            bundle.putString("nickN", nickN);
            bundle.putString("mail", mail);
            bundle.putString("pwd", pwd);
            bundle.putString("phone", phone);
            bundle.putString("address", address);
            intent.putExtras(bundle);

            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, registered_page2.class);

            EditText editText = findViewById(R.id.editText3);
            EditText editText1 = findViewById(R.id.editText4);
            EditText editText2 = findViewById(R.id.editText5);
            EditText editText4 = findViewById(R.id.editText7);
            EditText editText5 = findViewById(R.id.editText8);
            EditText editText6 = findViewById(R.id.editText9);

            String realN = editText.getText().toString();
            String nickN = editText1.getText().toString();
            String mail = editText2.getText().toString();
            String pwd = editText4.getText().toString();
            String phone = editText5.getText().toString();
            String address = editText6.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("realN", realN);
            bundle.putString("nickN", nickN);
            bundle.putString("mail", mail);
            bundle.putString("pwd", pwd);
            bundle.putString("phone", phone);
            bundle.putString("address", address);
            intent.putExtras(bundle);

            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(registered_page.this);

        builder.setTitle("確認");
        builder.setMessage("取消註冊");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registered_page.this.finish();
            }
        });

        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
