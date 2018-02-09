package com.example.radenko.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);


        Intent intent=getIntent();
        Button nazad=(Button)findViewById(R.id.btn_Back);
        final Button Dostava=(Button)findViewById(R.id.btn_Dostava);
        final Button NeuspjelaDostava=(Button)findViewById(R.id.btn_NeuspjelaDostava);
        Button PovratPosiljke=(Button)findViewById(R.id.btn_PovratPosiljke);
        Button Info=(Button)findViewById(R.id.btn_Info);

        EditText Sifra=(EditText)findViewById(R.id.txt_Sifra);
        String ime=intent.getStringExtra("ime");

        Intent i =new Intent(getApplicationContext(),GPS_Service.class);
        startService(i);

        Dostava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dostavaIntent=new Intent(SecondScreen.this,Dostava.class);
                startActivity(dostavaIntent);
            }
        });

        NeuspjelaDostava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neuspjesnaDostava=new Intent(SecondScreen.this,NeuspjelaDostava.class);
                startActivity(neuspjesnaDostava);

            }
        });

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nazadLogin=new Intent(SecondScreen.this,MainActivity.class);
                startActivity(nazadLogin);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myapp", "onClick: Kliknuli ste dugme");
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });


    }
}
