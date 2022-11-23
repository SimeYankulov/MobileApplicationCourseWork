package com.example.mobileapps_coursework;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class DBActivity extends AppCompatActivity {

    public static int loggedid;

    protected void ExecSql(String SQL, Object[] args, OnQuerySuccess success)
            throws Exception {
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/user.db", null);

        if (args != null) {
            db.execSQL(SQL, args);
        } else {
            db.execSQL(SQL);
        }
        db.close();

        success.OnSuccess();
    }

    protected void InitDB() throws Exception {
        ExecSql(

                        "CREATE TABLE if not exists USER(" +
                        "ID integer not null PRIMARY KEY AUTOINCREMENT," +
                        "Username text not null," +
                        "Password text not null," +
                        "Tel text not null," +
                        "Email text not null," +
                        "unique(Email)," +
                        "unique(Username,Tel)" +
                        ");",
                null,

                () -> Toast.makeText(getApplicationContext(),
                        "DB INIT SUCCESSFUL", Toast.LENGTH_LONG).show());
    }

     @SuppressLint("Range")
     protected void SelectSQL(String SelectQ, String[] args , OnSelectSuccess success)
            throws Exception
    {
       SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/user.db", null);

        Cursor cursor = db.rawQuery(SelectQ, args);

        while(cursor.moveToNext()) {
            @SuppressLint("Range") String ID = cursor.getString(cursor.getColumnIndex("ID"));
            setLoggedid(Integer.parseInt(ID));
            @SuppressLint("Range") String Username = cursor.getString(cursor.getColumnIndex("Username"));
            @SuppressLint("Range") String Password = cursor.getString(cursor.getColumnIndex("Password"));
            @SuppressLint("Range") String Tel = cursor.getString(cursor.getColumnIndex("Tel"));
            @SuppressLint("Range") String Email = cursor.getString(cursor.getColumnIndex("Email"));
            success.OnElementSelected(ID, Username,Password, Tel, Email);
        }
        db.close();

    }

    protected interface OnQuerySuccess {
        public void OnSuccess();
    }

    protected interface OnSelectSuccess{
        public void OnElementSelected(String ID,String Username,String Password,String Tel, String Email);
    }
    int getLoggedid(){
        return loggedid;
    }
    void setLoggedid(int id) {
        loggedid = id;
    }

}
