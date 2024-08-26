package com.example.userloginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtEmailAddressLog,edtPasswordLog;
    Button btnLoginLog;
    Button btnRegisterLog;
    LinearLayout btngoogleLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        btnLoginLog=findViewById(R.id.btnLoginLog);
        btnRegisterLog=findViewById(R.id.btnRegisterLog);
        btngoogleLog=findViewById(R.id.btngoogleLog);
        btnRegisterLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });
        btnLoginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(MainActivity.this, login.class);
                startActivity(i);
            }
        });
    }
}