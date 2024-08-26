package com.example.userloginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbConnect extends SQLiteOpenHelper {
    private static String dbname = "findusermanager";
    private static int dbVersion = 1;
    private static String dbtable = "users";
    private static String ID = "id";
    private static String firstname = "firstname";
    private static String lastname = "lastname";
    private static String emailaddress = "emailaddress";
    private static String password = "password";
    private static String confirmpassword = "confirmpassword";

    public dbConnect(@Nullable Context context) {
        super(context, dbname, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + dbtable + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                firstname + " TEXT, " +
                lastname + " TEXT, " +
                emailaddress + " TEXT, " +
                password + " TEXT, " +
                confirmpassword + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbtable);
        onCreate(sqLiteDatabase);
    }

    public void addUser(users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(firstname, user.getFirstname());
        values.put(lastname, user.getLastname());
        values.put(emailaddress, user.getEmailaddress());
        values.put(password, user.getPassword());
        values.put(confirmpassword, user.getConfirmpassword());
        db.insert(dbtable, null, values);
        db.close(); // Close the database after insertion
    }

    public boolean checkUser(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID};
        String selection = emailaddress + " = ? AND " + password + " = ?";
        String[] selectionArgs = {email, pass};
        Cursor cursor = db.query(dbtable, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close(); // Close the database after querying

        return cursorCount > 0;
    }
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + dbtable + " WHERE " + emailaddress + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public String getPasswordByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String passwordColumn = "password"; // Ensure this matches the column name in your database

        // Query to select password for the given email
        String query = "SELECT " + passwordColumn + " FROM " + dbtable + " WHERE " + emailaddress + " = ?";
        Cursor cursor = null;
        String password = null;

        try {
            cursor = db.rawQuery(query, new String[]{email});
            if (cursor != null && cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(passwordColumn);
                if (passwordIndex != -1) {
                    password = cursor.getString(passwordIndex);
                }
            }
        } catch (Exception e) {
            // Handle exceptions here, e.g., log the error
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return password;
    }



}
