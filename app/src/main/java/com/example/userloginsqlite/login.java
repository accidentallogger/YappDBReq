package com.example.userloginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    TextView txtlogin, txtenteremail, txtenterpassword, txtforgotpassword, txtloginwithgoogle;
    EditText ptenteremail, ptenterpassword;
    Button btnlogin;
    LinearLayout btnloginwithgoogle;
    dbConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        txtlogin = findViewById(R.id.txtlogin);
        txtenteremail = findViewById(R.id.txtenteremail);
        txtenterpassword = findViewById(R.id.txtenterpassword);
        txtforgotpassword = findViewById(R.id.txtforgotpassword);
        txtloginwithgoogle = findViewById(R.id.txtloginwithgoogle);
        ptenteremail = findViewById(R.id.ptenteremail);
        ptenterpassword = findViewById(R.id.ptenterpassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnloginwithgoogle = findViewById(R.id.btnloginwithgoogle);

        db = new dbConnect(this);

        txtforgotpassword.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        btnlogin.setOnClickListener(view -> {
            String email = ptenteremail.getText().toString();
            String password = ptenterpassword.getText().toString();

            if (db.checkEmailExists(email)) {
                String storedPassword = db.getPasswordByEmail(email);
                if (storedPassword != null && storedPassword.equals(password)) {
                    // Login successful
                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Invalid password
                    Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Email does not exist
                Toast.makeText(login.this, "Email does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
