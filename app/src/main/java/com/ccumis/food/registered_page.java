package com.ccumis.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registered_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page);

    }
    public boolean isContainAnyPunctuation(String str)//判斷是否含有特殊字元
    {
        Pattern patPunctuation = Pattern.compile("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
        Matcher matcher = patPunctuation.matcher(str);
        return matcher.find();
    }
    public boolean verifyfields()
    {
        EditText editText = findViewById(R.id.editText3);
        EditText editText1 = findViewById(R.id.editText4);
        EditText editText2 = findViewById(R.id.editText5);
        EditText editText4 = findViewById(R.id.editText7);
        EditText editText5 = findViewById(R.id.editText8);
        EditText editText6 = findViewById(R.id.editText9);
        EditText editText10 = findViewById(R.id.editText10);

        String realN = editText.getText().toString();
        String nickN = editText1.getText().toString();
        String mail = editText2.getText().toString();
        String pwd = editText4.getText().toString();
        String pwd2 = editText10.getText().toString();
        String phone = editText5.getText().toString();
        String address = editText6.getText().toString();
        String numbereg =".*\\d+.*";//字串中數字的正規表示式
        String regex = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";


        if(realN.trim().equals("")||nickN.trim().equals("")||mail.trim().equals("")||pwd.trim().equals("")||phone.trim().equals("")||address.trim().equals("")){
            Toast.makeText(this, "申請內容尚未完成請勿跳過", Toast.LENGTH_LONG).show();
            return  false;
        }
        else if(!pwd.equals(pwd2)){
            Toast.makeText(this, "兩個密碼不符合", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!mail.matches(regex))//檢查信箱
        {
            Toast.makeText(this,"信箱地址有誤",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(realN.matches(numbereg)||isContainAnyPunctuation(realN))//檢查真實姓名是否正確
        {
            Toast.makeText(this,"姓名不可有數字或是特殊字元",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(phone.length()!=10||!phone.matches(numbereg)) //檢查手機號碼
        {
            Toast.makeText(this,"電話號碼異常",Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }



    }
    //Todo design a method to check weather the register info exist
    /*public bolean checkusername(string realN){

    }*/

    public void go(View view){
        CheckBox checkBox = findViewById(R.id.checkBox2);
        if(verifyfields()) {
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
