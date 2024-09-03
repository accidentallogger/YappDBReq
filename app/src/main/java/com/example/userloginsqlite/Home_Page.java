package com.example.userloginsqlite;

// In Home_Page.java
// In Home_Page.java
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home_Page extends AppCompatActivity {
    ImageView male_icon, female_icon, default_icon;
    dbConnect db;
    LinearLayout container_profile,container_home,container_wardrobe,container_work,container_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        male_icon = findViewById(R.id.male_icon);
        female_icon = findViewById(R.id.female_icon);
        default_icon = findViewById(R.id.default_icon);
        container_profile = findViewById(R.id.container_profile);
        container_home = findViewById(R.id.container_home);
        container_wardrobe = findViewById(R.id.container_wardrobe);
        container_work = findViewById(R.id.container_work);
        container_style = findViewById(R.id.container_style);

        // Initialize dbConnect instance
        db = new dbConnect(this);

        // Set default visibility
        male_icon.setVisibility(View.GONE);
        female_icon.setVisibility(View.GONE);
        default_icon.setVisibility(View.VISIBLE);

        // Retrieve email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);

        if (email != null) {
            // Fetch gender from database
            String gender = db.getGenderByEmail(email);

            // Set visibility based on gender
            if (gender != null) {
                switch (gender.toLowerCase()) {
                    case "male":
                        male_icon.setVisibility(View.VISIBLE);
                        female_icon.setVisibility(View.GONE);
                        default_icon.setVisibility(View.GONE);
                        break;
                    case "female":
                        male_icon.setVisibility(View.GONE);
                        female_icon.setVisibility(View.VISIBLE);
                        default_icon.setVisibility(View.GONE);
                        break;
                    default:
                        male_icon.setVisibility(View.GONE);
                        female_icon.setVisibility(View.GONE);
                        default_icon.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

        container_wardrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home_Page.this, wardrobe.class);
                startActivity(i);
            }
        });



        container_profile.setOnClickListener(view -> {
            Intent i = new Intent(Home_Page.this, user_profile.class);
            startActivity(i);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
