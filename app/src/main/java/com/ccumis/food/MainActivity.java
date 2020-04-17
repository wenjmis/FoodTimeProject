package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //註冊按鍵事件
    public void regist(View view) {
        Intent intent = new Intent(this,registered_page0.class);
        startActivity(intent);
    }
    public  void login(View view){
        Intent intent = new Intent(this,home_page.class);
        startActivity(intent);
    }
}
