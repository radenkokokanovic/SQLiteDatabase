package com.example.radenko.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dostava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dostava);

        Button potvrdi=(Button) findViewById(R.id.btn_Potvrdi);

        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent povratakIntent=new Intent(Dostava.this,SecondScreen.class);
                startActivity(povratakIntent);
            }
        });
    }
}
