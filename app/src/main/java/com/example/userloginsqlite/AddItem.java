package com.example.userloginsqlite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicReference;
//import database;

public class AddItem extends ComponentActivity {

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ImageButton imageButton;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private byte[] imageBlob;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        final int RADIO_UPPER_ID = R.id.radio_upper;
        final int RADIO_LOWER_ID = R.id.radio_lower;
        final int RADIO_OTHER_ID = R.id.radio_other;

        AtomicReference<RadioGroup> radioGroup = new AtomicReference<>(findViewById(R.id.radio_group));
        Spinner spinner = findViewById(R.id.spinner_category);

        radioGroup.get().setOnCheckedChangeListener((group, checkedId) -> {
            int itemsArray;

            if (checkedId == RADIO_UPPER_ID) {
                itemsArray = R.array.upper_body_array;
            } else if (checkedId == RADIO_LOWER_ID) {
                itemsArray = R.array.lower_body_array;
            } else if (checkedId == RADIO_OTHER_ID) {
                itemsArray = R.array.accessory_array;
            } else {
                itemsArray = R.array.upper_body_array; // default option
            }

            updateSpinner(itemsArray, spinner);
        });


        imageButton = findViewById(R.id.imageButton);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri imageUri = result.getData() != null ? result.getData().getData() : null;
                        if (imageUri != null) {
                            imageButton.setImageURI(imageUri);

                            // Convert the selected image to a BLOB
                            try {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageUri);
                                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                                imageBlob = imageToByteArray(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        imagePickerLauncher.launch(intent);
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                });

        imageButton.setOnClickListener(v -> handleImageSelection());

        database g = new database(this);

        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(v -> {

            radioGroup.set(findViewById(R.id.radio_group));
            int radioID = radioGroup.get().getCheckedRadioButtonId();

            String upper = ((RadioButton) findViewById(radioID)).getText().toString();
            String selectedCategory = ((Spinner) findViewById(R.id.spinner_category)).getSelectedItem().toString();
            String color = ((EditText) findViewById(R.id.input_color)).getText().toString();
            String material = ((EditText) findViewById(R.id.input_material)).getText().toString();
            boolean isPersonal = ((Switch) findViewById(R.id.switch2)).isChecked();

            ContentValues values = new ContentValues();
            values.put("ownership", isPersonal ? "Personal" : "Not Owned");
            values.put("color", color);
            values.put("material", material);
            values.put("upper_lower", upper);
            values.put("type", selectedCategory);
            values.put("image", imageBlob);

            long newRowId = g.getWritableDatabase().insert("Apparel", null, values);

            if (newRowId != -1L) {
                Toast.makeText(this, "Apparel added successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("DB_INSERT_ERROR", "Error inserting row with values: " + values);
                Toast.makeText(this, "Error adding apparel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSpinner(int itemsArray, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                itemsArray,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void handleImageSelection() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        }
    }

    private byte[] imageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
