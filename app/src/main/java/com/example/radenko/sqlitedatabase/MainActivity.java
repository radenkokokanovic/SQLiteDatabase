package com.example.radenko.sqlitedatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase mydatabase;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mydatabase=openOrCreateDatabase("TestDB",MODE_PRIVATE,null);;

        mydatabase.execSQL("DROP TABLE IF EXISTS TutorialsPoint;");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR,Adresa VARCHAR);");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin','admin');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('radenko','kokanovic','Gornje Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('slavko','kokanovic','Gornje Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('damir','zecevic','Bijeljina');");

        Button login=(Button) findViewById(R.id.btnLogin);
        final EditText txtUser=(EditText) findViewById(R.id.txt_user);
        final EditText txtPass=(EditText) findViewById(R.id.txt_pass);
        Button registracija=(Button) findViewById(R.id.btn_Registracija);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(MainActivity.this,"Kliknuli ste na login", Toast.LENGTH_LONG).show();


                Intent drugiScreen=new Intent(MainActivity.this,SecondScreen.class);
                Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint where Username='"+txtUser.getText().toString()+"' and Password = '"+txtPass.getText().toString()+"'",null);
                // resultSet.moveToFirst();
//        String username = resultSet.getString(0);
//        String password = resultSet.getString(1);
                int broj=resultSet.getCount();
                if (broj==1) {
                    drugiScreen.putExtra("ime", txtUser.getText().toString());
                    Toast.makeText(MainActivity.this, "Uspjesno ste se logovali", Toast.LENGTH_SHORT).show();
                    startActivity(drugiScreen);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Greska,podaci nisu tačni", Toast.LENGTH_SHORT).show();
                }



            }
        });

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Pokrenuli ste registraciju", Toast.LENGTH_SHORT).show();
                Intent registracijaScreen=new Intent(MainActivity.this,Registracija.class);
                startActivity(registracijaScreen);
            }
        });

        String ime="slavko";

        Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint where Username='"+ime+"'",null);
       // resultSet.moveToFirst();
//        String username = resultSet.getString(0);
//        String password = resultSet.getString(1);
           int broj=resultSet.getCount();
//
//        Log.i("mojLog",username);
//        Log.i("MojLog",password);
        Log.i("MojLog",Integer.toString(broj));

        while(resultSet.moveToNext()) {

            Log.i("password", resultSet.getString(1));

            Log.i("Username ",resultSet.getString(0));

            Log.i("Adresa", resultSet.getString(2));
        }

    }
}
