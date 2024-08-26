package com.example.userloginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    TextView txtregister, txtfirstname, txtlastname, txtemail, txtpassword, txtcnfpassword, txt8charhint, txtDisplayInfoReg;
    EditText ptfirstname, ptlastname, ptemail, ptpassword, ptcnfpassword;
    Button btnRegisterLog, btnLoginLog;
    dbConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        txtregister = findViewById(R.id.txtregister);
        txtfirstname = findViewById(R.id.txtfirstname);
        txtlastname = findViewById(R.id.txtlastname);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        txtcnfpassword = findViewById(R.id.txtcnfpassword);
        txt8charhint = findViewById(R.id.txt8charhint);
        txtDisplayInfoReg = findViewById(R.id.txtDisplayInfoReg);

        ptfirstname = findViewById(R.id.ptfirstname);
        ptlastname = findViewById(R.id.ptlastname);
        ptemail = findViewById(R.id.ptemail);
        ptpassword = findViewById(R.id.ptpassword);
        ptcnfpassword = findViewById(R.id.ptcnfpassword);
        btnRegisterLog = findViewById(R.id.btnRegisterLog);
        btnLoginLog = findViewById(R.id.btnLoginLog);

        db = new dbConnect(this);

        btnLoginLog.setOnClickListener(view -> {
            Intent i = new Intent(register.this, login.class);
            startActivity(i);
        });

        btnRegisterLog.setOnClickListener(view -> {
            String strfirstname = ptfirstname.getText().toString();
            String strlastname = ptlastname.getText().toString();
            String stremail = ptemail.getText().toString();
            String strpassword = ptpassword.getText().toString();
            String strcnfpassword = ptcnfpassword.getText().toString();

            if (strfirstname.isEmpty() || strlastname.isEmpty() || stremail.isEmpty() || strpassword.isEmpty() || strcnfpassword.isEmpty()) {
                txtDisplayInfoReg.setText("ALL Fields required");
            } else if (strpassword.length() < 8) {
                Toast.makeText(register.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            } else if (!strpassword.equals(strcnfpassword)) {
                Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (db.checkEmailExists(stremail)) {
                Toast.makeText(register.this, "Email already in use", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(stremail)) {
                Toast.makeText(register.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            } else {
                users newUser = new users(strfirstname, strlastname, stremail, strpassword, strcnfpassword);
                db.addUser(newUser);
                Toast.makeText(register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                // Clear input fields
                ptfirstname.setText("");
                ptlastname.setText("");
                ptemail.setText("");
                ptpassword.setText("");
                ptcnfpassword.setText("");
            }
        });
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|yahoo\\.com|hotmail\\.com|outlook\\.com)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
