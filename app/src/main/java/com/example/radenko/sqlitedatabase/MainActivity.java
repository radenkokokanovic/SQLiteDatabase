package com.example.radenko.sqlitedatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase mydatabase=openOrCreateDatabase("TestDB",MODE_PRIVATE,null);;

        mydatabase.execSQL("DROP TABLE IF EXISTS TutorialsPoint;");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR,Adresa VARCHAR);");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin','admin');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('radenko','kokanovic','Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('slavko','kokanovic','Gornje Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('test','module','Batkovic');");

        Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint",null);
       // resultSet.moveToFirst();
//        String username = resultSet.getString(0);
//        String password = resultSet.getString(1);
//        int ime=resultSet.getColumnCount();
//
//        Log.i("mojLog",username);
//        Log.i("MojLog",password);
//        Log.i("MojLog",Integer.toString(ime));
        while(resultSet.moveToNext()) {

            Log.i("password", resultSet.getString(1));

            Log.i("Username ",resultSet.getString(0));

            Log.i("Adresa", resultSet.getString(2));
        }

    }
}
