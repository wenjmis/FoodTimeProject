package com.ccumis.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class registered_page0 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_page0);

    }
    public void selected(View view){
        LinearLayout linearLayout1= findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2= findViewById(R.id.linearLayout2);
        if(linearLayout1.hasOnClickListeners()){
            Intent intent = new Intent(this,registered_page.class);
            startActivity(intent);
        }
        if(linearLayout2.hasOnClickListeners()){
            Intent intent = new Intent(this,registered_page.class);
            startActivity(intent);
            registered_page0.this.finish();
        }
    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(registered_page0.this);

        builder.setTitle("確認");
        builder.setMessage("取消註冊");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registered_page0.this.finish();
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
