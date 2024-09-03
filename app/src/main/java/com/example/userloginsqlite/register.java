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
    TextView txtDisplayInfoReg;
    EditText ptname, ptemail, ptpassword, ptcnfpassword;
    Button btnRegisterLog, btnLoginLog;
    dbConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        txtDisplayInfoReg = findViewById(R.id.txtDisplayInfoReg);
        ptname = findViewById(R.id.ptname);
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
            String strname = ptname.getText().toString();
            String stremail = ptemail.getText().toString();
            String strpassword = ptpassword.getText().toString();
            String strcnfpassword = ptcnfpassword.getText().toString();

            // Validate input fields
            if (strname.isEmpty() || stremail.isEmpty() || strpassword.isEmpty() || strcnfpassword.isEmpty()) {
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
                // Create new user object without Id, as it's auto-generated in the database
                users newUser = new users(strname, stremail, strpassword);
                db.addUser(newUser);

                // Get userId from the database after inserting the user
                int userId = db.getUserIdByEmail(stremail);

                // Transition to user profile setup
                Intent intent = new Intent(register.this, user_profile_setup.class);
                intent.putExtra("googleName", strname);
                intent.putExtra("googleEmail", stremail);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
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
