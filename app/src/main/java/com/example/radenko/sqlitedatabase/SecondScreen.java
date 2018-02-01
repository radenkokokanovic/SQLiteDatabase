package com.example.radenko.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        TextView labela=(TextView) findViewById(R.id.txt_Insert);
        Intent intent=getIntent();
        Button nazad=(Button)findViewById(R.id.btn_Back);
        String ime=intent.getStringExtra("ime");
        labela.setText(ime);

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nazadLogin=new Intent(SecondScreen.this,MainActivity.class);
                startActivity(nazadLogin);
            }
        });


    }
}
