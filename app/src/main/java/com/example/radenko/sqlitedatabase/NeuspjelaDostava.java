package com.example.radenko.sqlitedatabase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NeuspjelaDostava extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LocationListener listener;
    private LocationManager locationManager;
    public StringRequest request;
    RequestQueue requestQueue;
    public EditText opis;
    public String Radnik;
    public String vrijednost;
    public String insertURL = "http://korisnik.telrad.net/telradServis/stranice/insertErrorSend.php";
    String ime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuspjela_dostava);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        opis = (EditText) findViewById(R.id.txt_opis);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NeuspjelaDostava.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //      ArrayAdapter myAdapter=ArrayAdapter.createFromResource(this,R.array.names,android.R.layout.simple_spinner_item);

        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent=getIntent();
        Radnik=intent.getStringExtra("ime");
        final EditText sifra = (EditText) findViewById(R.id.txt_Sifra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        // actionBar.setTitle("RND APPLICATION");
        toolbar.setTitle("Neuspjela dostava");

        Button insert = (Button) findViewById(R.id.btn_Insert);

        Button potvrdi = (Button) findViewById(R.id.btn_Prihvati);
        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent povratakIntent = new Intent(NeuspjelaDostava.this, SecondScreen.class);
                povratakIntent.putExtra("ime",Radnik.toString());
                startActivity(povratakIntent);
            }
        });

        sifra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        insert.setOnClickListener(new View.OnClickListener() {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();


                        parameters.put("sifra", sifra.getText().toString());
                        parameters.put("lng", Double.toString(longitude));
                        parameters.put("ltd", Double.toString(latitude));
                        parameters.put("user_id", Radnik.toString());
                        parameters.put("opis", opis.getText().toString());
                        parameters.put("kategorija", vrijednost.toString());
                        Log.d("myapp", "getParams: " + Double.toString(location.getLatitude()));
                        Log.d("myapp", "getParams: " + ime);

                        return parameters;
                    }
                };
                requestQueue.add(request);

            }

        });
       // Toast.makeText(this, "Uspješno dodavanje podataka" , Toast.LENGTH_LONG).show();

    }

        //noinspection MissingPermission




    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0) {
            TextView mojTekst = (TextView) view;
            vrijednost=mojTekst.getText().toString();
            Toast.makeText(this, "Izabrana opcija je " + mojTekst.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
