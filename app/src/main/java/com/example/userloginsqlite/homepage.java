package com.example.userloginsqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;  // Import ConstraintLayout
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class homepage extends AppCompatActivity {
    ImageView male_icon, female_icon, default_icon,home_icon,wardrobe_icon;
    dbConnect db;
    ConstraintLayout container_profile;
    LinearLayout container_home, container_wardrobe, container_work, container_style;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        wardrobe_icon = findViewById(R.id.icon_wardrobe);
        home_icon = findViewById(R.id.icon_home);
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
                    case "m":
                        male_icon.setVisibility(View.VISIBLE);
                        female_icon.setVisibility(View.GONE);
                        default_icon.setVisibility(View.GONE);
                        break;
                    case "female":
                        male_icon.setVisibility(View.GONE);
                        female_icon.setVisibility(View.VISIBLE);
                        default_icon.setVisibility(View.GONE);
                        break;
                    case "f":
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

        container_profile.setOnClickListener(view -> {
            Intent i = new Intent(homepage.this, user_profile.class);
            startActivity(i);
        });

        home_icon.setOnClickListener(view -> {
            Intent j = new Intent(homepage.this, homepage.class);
            startActivity(j);
        });

        wardrobe_icon.setOnClickListener(view -> {
            Intent k = new Intent(homepage.this, wardrobe.class);
            startActivity(k);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
