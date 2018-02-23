package com.example.radenko.sqlitedatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SecondScreen extends AppCompatActivity {


    String json_string;
    String JSON_STRING;
    EditText Sifra;
    String ime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        new BackgroundTask().execute();
        Intent intent=getIntent();
        ime = intent.getStringExtra("ime");
        Button nazad=(Button)findViewById(R.id.btn_Back);
        final Button Dostava=(Button)findViewById(R.id.btn_Dostava);
        final Button NeuspjelaDostava=(Button)findViewById(R.id.btn_NeuspjelaDostava);
        Button PovratPosiljke=(Button)findViewById(R.id.btn_PovratPosiljke);
        Button Info=(Button)findViewById(R.id.btn_Info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //  actionBar.setTitle("RND APPLICATION");
        toolbar.setTitle("Akcije dostave");
         Sifra=(EditText)findViewById(R.id.txt_Sifra);


        CekSession();

//        Intent i =new Intent(getApplicationContext(),GPS_Service.class);
//        i.putExtra("ime",ime.toString());
//        startService(i);
//        try {


//        if (!intent.getStringExtra("ime").equals(null)) {
//
//        }  }catch (Exception x)
//        {
//            Log.d("greska========",x.toString());
//        }



//        Intent serviceIntent = new Intent(SecondScreen.this, GPS_Service.class);
//        serviceIntent.putExtra("ime", ime.toString());
//        this.startService(serviceIntent);


        Dostava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dostavaIntent=new Intent(SecondScreen.this,Dostava.class);
                dostavaIntent.putExtra("ime",ime.toString());
                startActivity(dostavaIntent);
            }
        });

        NeuspjelaDostava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent neuspjesnaDostava=new Intent(SecondScreen.this,NeuspjelaDostava.class);
                neuspjesnaDostava.putExtra("ime", ime.toString());
                startActivity(neuspjesnaDostava);

            }
        });

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefs.saveSharedSetting(SecondScreen.this, "CaptainCode", "true");
                //And when you click on Logout button, You will set the value to True AGAIN
                Intent LogOut = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(LogOut);
                finish();
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);
                Intent nazadLogin=new Intent(SecondScreen.this,MainActivity.class);
                startActivity(nazadLogin);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myapp", "onClick: Kliknuli ste dugme");
                Intent i =new Intent(SecondScreen.this,QR_Scaner.class);
                i.putExtra("ime",ime.toString());

                startActivity(i);
            }
        });


        PovratPosiljke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  getJSON(v);
                parseJSON(v);
            }
        });





        Sifra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void getJSON(View view)
    {
        new BackgroundTask().execute();
    }

    public void parseJSON(View view) {

        new BackgroundTask().execute();

        if (json_string==null)
        {

           // Toast.makeText(getApplicationContext(), "Prvo uzmite Json podatke", Toast.LENGTH_SHORT).show();
            new BackgroundTask().execute();
        }else
        {
            Intent intent=new Intent(this,DisplayListView.class);
            intent.putExtra("json_data",json_string);
            intent.putExtra("ime",ime.toString());
            startActivity(intent);
        }
    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute() {
            json_url="http://korisnik.telrad.net/telradServis/stranice/getPackets.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
//            TextView textView=(TextView)findViewById(R.id.txt_View);
  //          textView.setText(result);
            json_string=result;
        }
    }

    public void CekSession(){

        Boolean Check = Boolean.valueOf(SharedPrefs.readSharedSetting(SecondScreen.this, "CaptainCode", "true"));

        Intent introIntent = new Intent(SecondScreen.this, MainActivity.class);
        introIntent.putExtra("CaptainCode", Check);

        //The Value if you click on Login Activity and Set the value is FALSE and whe false the activity will be visible
        if (Check) {
            startActivity(introIntent);
            finish();
        } //If no the Main Activity not Do Anything
    }
}
