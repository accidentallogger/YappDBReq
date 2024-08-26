package com.example.userloginsqlite;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText ptForgotName, ptForgotEmail;
    Button btnSubmitForgot;
    TextView txtForgotPasswordMessage;
    dbConnect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        ptForgotName = findViewById(R.id.ptForgotName);
        ptForgotEmail = findViewById(R.id.ptForgotEmail);
        btnSubmitForgot = findViewById(R.id.btnSubmitForgot);
        txtForgotPasswordMessage = findViewById(R.id.txtForgotPasswordMessage);

        db = new dbConnect(this);

        btnSubmitForgot.setOnClickListener(view -> {
            String name = ptForgotName.getText().toString();
            String email = ptForgotEmail.getText().toString();

            if (name.isEmpty() || email.isEmpty()) {
                txtForgotPasswordMessage.setText("All fields are required");
                txtForgotPasswordMessage.setVisibility(View.VISIBLE);
            } else if (!db.checkEmailExists(email)) {
                txtForgotPasswordMessage.setText("Email not found");
                txtForgotPasswordMessage.setVisibility(View.VISIBLE);
            } else {
                // Logic to retrieve and display the password
                String password = db.getPasswordByEmail(email);
                if (password != null) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password: " + password, Toast.LENGTH_LONG).show();
                } else {
                    txtForgotPasswordMessage.setText("Unable to retrieve password");
                    txtForgotPasswordMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
