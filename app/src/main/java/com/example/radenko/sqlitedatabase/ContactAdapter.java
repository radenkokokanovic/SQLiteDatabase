package com.example.radenko.sqlitedatabase;

/**
 * Created by Radenko on 2/22/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radenko on 2/12/2018.
 */

public class ContactAdapter extends ArrayAdapter{
    List list=new ArrayList();
    public ContactAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolder contactHolder;
        if (row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            contactHolder=new ContactHolder();
            contactHolder.tx_name=(TextView)row.findViewById(R.id.tx_name);
            contactHolder.tx_lastName=(TextView)row.findViewById(R.id.tx_lastName);
            contactHolder.tx_address=(TextView)row.findViewById(R.id.tx_address);
            row.setTag(contactHolder);
        }else
        {
            contactHolder=(ContactHolder)row.getTag();
        }
        Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getIme());
        contactHolder.tx_lastName.setText(contacts.getPrezime());
        contactHolder.tx_address.setText(contacts.getAdresa());
        return row;
    }

    static class ContactHolder
    {
        TextView tx_name,tx_lastName,tx_address;
    }
}
