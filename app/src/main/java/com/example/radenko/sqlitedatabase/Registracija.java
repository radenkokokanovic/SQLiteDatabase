package com.example.radenko.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registracija extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        Button registruj=(Button)findViewById(R.id.btn_Registruj);
        Button nazad=(Button) findViewById(R.id.btn_Nazad);


        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginScreen=new Intent(Registracija.this,MainActivity.class);
                startActivity(loginScreen);
            }
        });
    }
}
