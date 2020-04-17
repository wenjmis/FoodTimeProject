package com.ccumis.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class registered_page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page2);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("親愛的用戶您好：\n"+"      "+getResources().getString(R.string.notice));
    }
    public void done(View view){
        CheckBox checkBox = findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            registered_page2.this.finish();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(registered_page2.this);

            builder.setTitle("錯誤");
            builder.setMessage("NMSL 看完還敢不打勾啊");
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
