package com.example.radenko.sqlitedatabase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase mydatabase;
    public Button login;
    public   EditText txtUser;
    public  EditText txtPass;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //  actionBar.setTitle("RND APPLICATION");
        toolbar.setTitle("Login");






        mydatabase=openOrCreateDatabase("TestDB",MODE_PRIVATE,null);;

        mydatabase.execSQL("DROP TABLE IF EXISTS TutorialsPoint;");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR,Adresa VARCHAR);");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin','admin');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('radenko','kokanovic','Gornje Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('slavko','kokanovic','Gornje Crnjelovo');");
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('damir','zecevic','Bijeljina');");

         login=(Button) findViewById(R.id.btnLogin);
         txtUser=(EditText) findViewById(R.id.txt_user);
         txtPass=(EditText) findViewById(R.id.txt_pass);


         txtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 if (!hasFocus) {
                     hideKeyboard(v);
                 }
             }
         });

        txtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });



        if(!runtime_permissions())
            enable_buttons();



//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  Toast.makeText(MainActivity.this,"Kliknuli ste na login", Toast.LENGTH_LONG).show();
//
//
//                Intent drugiScreen=new Intent(MainActivity.this,SecondScreen.class);
//                Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint where Username='"+txtUser.getText().toString()+"' and Password = '"+txtPass.getText().toString()+"'",null);
//                // resultSet.moveToFirst();
////        String username = resultSet.getString(0);
////        String password = resultSet.getString(1);
//                int broj=resultSet.getCount();
//                if (broj==1) {
//                    drugiScreen.putExtra("ime", txtUser.getText().toString());
//                    Toast.makeText(MainActivity.this, "Uspjesno ste se logovali RND", Toast.LENGTH_SHORT).show();
//                    startActivity(drugiScreen);
//
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Greska,podaci nisu tačni", Toast.LENGTH_SHORT).show();
//                }
//
//
//
//            }
//        });


//test
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
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void enable_buttons() {


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
                SharedPrefs.saveSharedSetting(MainActivity.this, "CaptainCode", "false");
                Intent ImLoggedIn = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(ImLoggedIn);
                finish();

                if (broj==1) {
                    drugiScreen.putExtra("ime", txtUser.getText().toString());
                    Toast.makeText(MainActivity.this, "Uspjesno ste se logovali RND", Toast.LENGTH_SHORT).show();
                    Intent serviceIntent = new Intent(MainActivity.this, GPS_Service.class);
                    serviceIntent.putExtra("ime", txtUser.getText().toString());
                    startService(serviceIntent);
                    startActivity(drugiScreen);


                }
                else
                {
                    Toast.makeText(MainActivity.this, "Greska,podaci nisu tačni", Toast.LENGTH_SHORT).show();
                }



            }
        });




//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("myapp", "onClick: Kliknuli ste dugme");
//                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
//                startService(i);
//            }
//        });
    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }
}
