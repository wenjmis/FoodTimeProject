package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class businregist_page extends AppCompatActivity {
    private int type;
    private String address,phone,pwd,mail,nickN,realN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businregist_page);
        Bundle bundle = this.getIntent().getExtras();

        realN = bundle.getString("realN");
        nickN = bundle.getString("nickN");
        mail = bundle.getString("mail");
        pwd = bundle.getString("pwd");
        phone = bundle.getString("phone");
        address = bundle.getString("address");
        type = 1;

    }
    public void go(View view) {

        EditText editText= findViewById(R.id.comp_name_text);
        EditText editText1= findViewById(R.id.comp_address_text);
        EditText editText2= findViewById(R.id.comp_phone_text);

        String comp_name_text = editText.getText().toString();
        String comp_address_text= editText1.getText().toString();
        String comp_phone_text = editText2.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("realN", realN);
        bundle.putString("nickN", nickN);
        bundle.putString("mail", mail);
        bundle.putString("pwd", pwd);
        bundle.putString("phone", phone);
        bundle.putString("address", address);
        bundle.putInt("type", type);

        bundle.putString("comp_name_text", comp_name_text);
        bundle.putString("comp_address_text", comp_address_text);
        bundle.putString("comp_phone_text", comp_phone_text);


        Intent intent = new Intent(this, registered_page2.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


}
