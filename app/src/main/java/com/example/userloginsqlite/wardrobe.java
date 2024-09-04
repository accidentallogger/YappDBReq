package com.example.userloginsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;

@SuppressLint("MissingInflatedId")
public class wardrobe extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wardrobe_activity);

        database dbhelper= new database(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();


        ImageView imageView = findViewById(R.id.image1);
        Bitmap imageBitmap = fetchImg(db, "upper"); // Pass "upper" or "lower" depending on what you need
        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
        } else {
            System.out.println("Fail");
        }

       Button add_img= findViewById(R.id.btnadd);
        add_img.setOnClickListener(view ->{
            Intent  addImg = new Intent(wardrobe.this, AddItem.class);
            startActivity(addImg);
        });

        ImageView profile= findViewById(R.id.default_icon);
        profile.setOnClickListener(view ->{
            Intent  prof = new Intent(wardrobe.this, user_profile.class);
            startActivity(prof);
        });

        //Its not working!!
        ImageView icon= findViewById(R.id.icon_home);
        icon.setOnClickListener(view -> {
            Intent hoe = new Intent(wardrobe.this, homepage.class);
            startActivity(hoe);
        });
    }

    @SuppressLint("Range")
    public Bitmap fetchImg(SQLiteDatabase db, String upp_low) {
        Bitmap imageBitmap = null;
        Cursor cursor = null;

        try {
            // Use a parameterized query to prevent SQL injection
            String query = "SELECT image FROM apparel WHERE upper_lower = ?";
            cursor = db.rawQuery(query, new String[]{upp_low});

            if (cursor != null && cursor.moveToFirst()) {
                // Retrieve the BLOB from the cursor
                 byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex("image"));

                // Convert the BLOB to a Bitmap
                if (imageBlob != null) {
                    imageBitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imageBitmap;
    }

}
