package com.example.radenko.sqlitedatabase;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Scaner extends AppCompatActivity {

    public Button scan;
    public String ime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__scaner);

        Intent intent=getIntent();
        ime = intent.getStringExtra("ime");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //  actionBar.setTitle("RND APPLICATION");
        toolbar.setTitle("Skeniranje artikla");

        Button nazad=(Button)findViewById(R.id.btn_Nazad);

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QR_Scaner.this,SecondScreen.class);
                intent.putExtra("ime",ime.toString());
                startActivity(intent);
            }
        });

        scan=(Button)findViewById(R.id.btn_Scan);
        //Main Activity
        final Activity activity=this;

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null)
        {
            if (result.getContents()==null)
            {
                Toast.makeText(this, "Prekinuli ste skeniranje", Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
