package com.example.radenko.sqlitedatabase;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DisplayListView extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    String ime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_view);
        Button nazad=(Button)findViewById(R.id.btn_Nazad);
        Intent intent=getIntent();
        ime = intent.getStringExtra("ime");

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DisplayListView.this,SecondScreen.class);
                intent.putExtra("ime",ime.toString());
                startActivity(intent);
            }
        });
        listView=(ListView)findViewById(R.id.listview);

        contactAdapter=new ContactAdapter(this,R.layout.row_layout);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contact=new Contacts();
                contact=(Contacts)contactAdapter.getItem(position);

                Toast.makeText(DisplayListView.this, contact.getIme().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        json_string=getIntent().getExtras().getString("json_data");
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("Server response");
            int count=0;
            String name,lastName,address;
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                name=JO.getString("sifra_paketa");
                lastName=JO.getString("naziv");
                address=JO.getString("adresa");

                Contacts contacts=new Contacts(name,lastName,address);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
