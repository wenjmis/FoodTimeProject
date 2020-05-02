package com.ccumis.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

public class registered_page extends AppCompatActivity {
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page);
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt("type");

    }
    public void go(View view){
        Intent intent = new Intent(this,registered_page2.class);

        EditText editText = findViewById(R.id.editText3);
        EditText editText1 = findViewById(R.id.editText4);
        EditText editText2= findViewById(R.id.editText5);
        EditText editText3= findViewById(R.id.editText6);
        EditText editText4 = findViewById(R.id.editText7);
        EditText editText5 = findViewById(R.id.editText8);
        EditText editText6 = findViewById(R.id.editText9);
        String realN=editText.getText().toString();
        String nickN=editText1.getText().toString();
        String mail=editText2.getText().toString();
        String account=editText3.getText().toString();
        String pwd=editText4.getText().toString();
        String phone=editText5.getText().toString();
        String address=editText6.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putString("realN",realN);
        bundle.putString("nickN",nickN);
        bundle.putString("account",account);
        bundle.putString("mail",mail);
        bundle.putString("pwd",pwd);
        bundle.putString("phone",phone);
        bundle.putString("address",address);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        registered_page.this.finish();
    }
}
