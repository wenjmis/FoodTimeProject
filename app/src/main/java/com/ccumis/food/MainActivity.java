package com.ccumis.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("User");
    private FirebaseAuth fAuth  = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    //註冊按鍵事件
    public void regist(View view) {
        Intent intent = new Intent(this, registered_page.class);
        startActivity(intent);
    }
    public void login(View view){

        EditText editText = findViewById(R.id.editText);
        EditText editText1 = findViewById(R.id.editText2);
        String email = editText.getText().toString();
        String password = editText1.getText().toString();
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "歡迎", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,home_page.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*EditText editText = findViewById(R.id.editText);
        EditText editText1 = findViewById(R.id.editText2);
        String account = editText.getText().toString();
        final String password = editText1.getText().toString();
        collectionReference.whereEqualTo("account",account)
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        List<Map<String, Object>> list = new ArrayList<>();
                        list.add(document.getData());
                        if(list.get(0).get("pwd").equals(password)){
                            pass=true;
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
        });
        if(pass){
            editText.setText("");
            editText1.setText("");
            Intent intent = new Intent(this,home_page.class);
            startActivity(intent);
        }*/
    }
}
