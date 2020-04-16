package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class registered_page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page2);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("親愛的用戶您好：\n"+"      "+getResources().getString(R.string.notice));
    }
}
