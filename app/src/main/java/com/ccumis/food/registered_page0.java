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
    //餐主
    public void selected_Layout1(View view){
            Intent intent = new Intent(this,registered_page.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type",0);
            intent.putExtras(bundle);
            startActivity(intent);
    }
    //食客
    public void selected_Layout2(View view){
        Intent intent = new Intent(this,registered_page.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type",1);
        intent.putExtras(bundle);
        startActivity(intent);
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
