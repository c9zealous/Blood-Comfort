package com.bloodcomfort.www.bloodcomfort;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    private static final String MY_PREFS_NAME = "MyPrefsFile";

    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        listView = (ListView) findViewById(R.id.listview);
        contactAdapter = new ContactAdapter(this, R.layout.row_layout);
        listView.setAdapter(contactAdapter);
        json_string = getIntent().getExtras().getString("json_data");



        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {

                        TextView textView = (TextView) view.findViewById(R.id.tx_name);
                        TextView textView1 = (TextView) view.findViewById(R.id.tx_email);
                        TextView textView2=(TextView)view.findViewById(R.id.tx_mobile);


                       String text1 = textView1.getText().toString();
                        String text2 = textView2.getText().toString();
                        Toast.makeText(getApplicationContext(),"Send an email in order to view the donors email-id and contact number", Toast.LENGTH_LONG).show();


                        Intent i = new Intent(DisplayListView.this, SendEmail.class);
                        i.putExtra("email", text1);
                        i.putExtra("mobile", text2);
                        startActivity(i);


                    }
                }
        );



        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String name, email, mobile, bloodtype;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                email = JO.getString("email");
                mobile = JO.getString("mobile");
                bloodtype = JO.getString("bloodtype");

                Contacts contacts = new Contacts(name, email, mobile, bloodtype);

                contactAdapter.add(contacts);

                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("facebookname1", "");
            String restoredText1 = prefs.getString("facebookemail1", "");
            String key = prefs.getString("key", "");
            if (key== "true") {
                Intent a = new Intent(this, Display1.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                this.finish();
                return true;
            } else if (key=="false") {
                Intent a = new Intent(this, Display.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
