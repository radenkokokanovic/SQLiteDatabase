package com.example.radenko.sqlitedatabase;

/**
 * Created by Radenko on 2/8/2018.
 */

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

/**
 * Created by filipp on 6/16/2016.
 */
public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    public StringRequest request;
    RequestQueue requestQueue;
    public String insertURL="http://korisnik.telrad.net/telradServis/stranice/getPodatkeAndy.php";
    String ime;

    @Override
    public ComponentName startService(Intent service) {


        return super.startService(service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ime=intent.getStringExtra("ime");
        Log.d("test--->",ime.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public void onCreate() {


        Log.d("myapp", "pokrenut servis");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                Intent i = new Intent("location_update");

                Log.d("myapp", "onLocationChanged: '"+location.getLongitude()+"'");

                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());

                //Dodavanje koda
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
                        Map<String,String> parameters  = new HashMap<String, String>();


                        parameters.put("macAdresa",ime.toString());
                        parameters.put("lat", Double.toString(location.getLatitude()));
                        parameters.put("long", Double.toString(location.getLongitude()));
                        Log.d("myapp", "getParams: "+Double.toString( location.getLatitude()));
                        Log.d("myapp", "getParams: "+ime);

                        return parameters;
                    }
                };
                requestQueue.add(request);



                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 0, listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("myapp", "onDestroy: Ugasen je servis");
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}
