package com.bloodcomfort.www.bloodcomfort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter  extends ArrayAdapter {

    List list=new ArrayList();

    public ContactAdapter(Context context, int resource) {
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

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolder contactHolder;

        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            contactHolder=new ContactHolder();
            contactHolder.tx_name=(TextView)row.findViewById(R.id.tx_name);
            contactHolder.tx_email=(TextView)row.findViewById(R.id.tx_email);
            contactHolder.tx_mobile=(TextView)row.findViewById(R.id.tx_mobile);
            contactHolder.tx_bloodtype=(TextView)row.findViewById(R.id.tx_bloodtype);


            row.setTag(contactHolder);
        }else {
            contactHolder=(ContactHolder)row.getTag();
        }

        Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getName());
        contactHolder.tx_email.setText(contacts.getEmail());
       contactHolder.tx_mobile.setText(contacts.getMobile());
        contactHolder.tx_bloodtype.setText(contacts.getBloodtype());

        return row;
    }



    static class ContactHolder
    {
        TextView tx_name,tx_email,tx_mobile,tx_bloodtype;
    }
}
