package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    public boolean infoCheck(){
        EditText editTextcomp_name_text=findViewById(R.id.comp_name_text);
        EditText editTextcomp_address_text=findViewById(R.id.comp_address_text);
        EditText  editTextcomp_phone_text=findViewById(R.id.comp_phone_text);
        String comp_name_text=editTextcomp_name_text.getText().toString();
        String comp_address_text=editTextcomp_address_text.getText().toString();
        String comp_phone_text=editTextcomp_phone_text.getText().toString();



        if(comp_name_text.trim().equals("")||comp_address_text.trim().equals("")||comp_phone_text.trim().equals("")){
            Toast.makeText(this,"資訊不得空白",Toast.LENGTH_SHORT).show();
            return  false;
        }

        else if(!comp_phone_text.substring(0,1).equals("0")||comp_phone_text.length()>11||comp_phone_text.length()<10){
            System.out.println(comp_phone_text.substring(0,1));
            System.out.println(comp_phone_text.length());
            System.out.println(comp_phone_text);
            Toast.makeText(this,"請輸入正確的號碼",Toast.LENGTH_SHORT).show();
            return  false;
        }

        else {
            return true;
        }
    }


    public void go(View view) {
        if(infoCheck()) {

            EditText editText = findViewById(R.id.comp_name_text);
            EditText editText1 = findViewById(R.id.comp_address_text);
            EditText editText2 = findViewById(R.id.comp_phone_text);

            String comp_name_text = editText.getText().toString();
            String comp_address_text = editText1.getText().toString();
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


}
