package com.bloodcomfort.www.bloodcomfort;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class OnlineNeedBlood extends ActionBarActivity {
    Spinner spinner3;
    ArrayAdapter<CharSequence> adapter3;
    public String blood_type;

    String json_string;

    String JSON_STRING;

    Spinner sp, sp1, sp2;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> listItems1 = new ArrayList<>();
    ArrayList<String> listItems2 = new ArrayList<>();
    public String Country_value, country_id1, State_value, state_id1;
    int country_id, state_id;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    EditText phoneNumber1;
    String phonestr1;

    public String country, state, city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlineneedblood);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.blood_type_names, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        AdView adView = (AdView)findViewById(R.id.adView);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        adView.loadAd(adRequest);

        sp = (Spinner) findViewById(R.id.spinner);
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, listItems);
        adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, listItems1);
        adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, listItems2);

        sp.setAdapter(adapter);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood_type = spinner3.getSelectedItem().toString();

                //   Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {
                country = sp.getSelectedItem().toString();
                country_id = (int) sp.getItemIdAtPosition(position);
                country_id1 = String.valueOf(country_id + 1);

                adapter1.clear();
                String method = "login";
                BackgroundTask1 backgroundTask1 = new BackgroundTask1();
                backgroundTask1.execute(method, country_id1, Country_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {

                state = sp1.getSelectedItem().toString();
                state_id = (int) sp1.getItemIdAtPosition(position);
                //   StringTokenizer t=new StringTokenizer(state," ");
                String[] splited = state.split("\\s+");

                state_id1 = String.valueOf(state_id + 1);


                String method = "sign";
                BackgroundTask2 backgroundTask2 = new BackgroundTask2();
                backgroundTask2.execute(method, splited[1], state);
                adapter2.clear();
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {
                city = sp2.getSelectedItem().toString();

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //  Intent intent = getIntent();
        //   String name = intent.getStringExtra("name1");
        //   String email = intent.getStringExtra("email1");

    }

    public void onStart() {
        super.onStart();

        new CountryValue().execute();
    }


    private class CountryValue extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }

        protected Void doInBackground(Void... params) {
            String login_url1 = "http://www.bloodcomfort.com/spinner_country.php";
            InputStream is = null;
            String result = "";
            try {

                URL url = new URL(login_url1);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }

                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        // add interviewee name to arraylist
                        StringBuilder s1 = new StringBuilder(100);
                        //   s1.append(jsonObject.getString("id"));
                        s1.append(jsonObject.getString("name"));

                        list.add(String.valueOf(s1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(Void result) {
            listItems.addAll(list);
            adapter.notifyDataSetChanged();

        }
    }

    class BackgroundTask1 extends AsyncTask<String, Void, String> {
        ArrayList<String> list1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list1 = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url2 = "http://www.bloodcomfort.com/spinner_state.php";
            String response = "";
            String id = params[1];
            String login_pass = params[2];

            try {
                URL url = new URL(login_url2);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }

                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        // add interviewee name to arraylist

                        StringBuilder s2 = new StringBuilder(100);

                        s2.append(jsonObject.getString("name"));
                        s2.append(" ");
                        s2.append(jsonObject.getString("id"));

                        list1.add(String.valueOf(s2));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return null;


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
            listItems1.addAll(list1);
            adapter1.notifyDataSetChanged();
        }

    }

    class BackgroundTask2 extends AsyncTask<String, Void, String> {
        ArrayList<String> list2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list2 = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "http://www.bloodcomfort.com/spinner_city.php";
            String response = "";
            String id = params[1];
            String login_pass = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }

                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        // add interviewee name to arraylist
                        StringBuilder s3 = new StringBuilder(100);
                        //  s3.append(jsonObject.getString("id"));
                        s3.append(jsonObject.getString("name"));

                        list2.add(String.valueOf(s3));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return null;


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
            listItems2.addAll(list2);
            adapter2.notifyDataSetChanged();
        }

    }

    public void needButton(View view) {

        String method = "register";

        String face_name = getIntent().getStringExtra("face_name");
        String face_email = getIntent().getStringExtra("face_email");


        if (!face_name.equals("") && !face_email.equals("")) {
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, face_name, face_email, country, state, city, blood_type);
            finish();

        } else {
            Toast pass = Toast.makeText(OnlineNeedBlood.this, "Facebook Email-Id Not Valid!", Toast.LENGTH_SHORT);
            pass.show();

        }

    }

    class BackgroundTask extends AsyncTask<String, Void, String> {

        Context ctx;
        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://www.bloodcomfort.com/facebook_need.php";


            String namestr = params[1];
            String emailstr = params[2];
            String country = params[3];
            String state = params[4];
            String city = params[5];
            String bloodstr = params[6];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(namestr, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(emailstr, "UTF-8") + "&" +
                        URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") + "&" +
                        URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8") + "&" +
                        URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") + "&" +
                        URLEncoder.encode("bloodtype", "UTF-8") + "=" + URLEncoder.encode(bloodstr, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder=new StringBuilder();

                while ((JSON_STRING=bufferedReader.readLine())!=null){

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

            json_string=result;

             if(json_string.equals("{\"server_response\":[]}")){
                Toast select = Toast.makeText(OnlineNeedBlood.this, "Selected Blood Type Is Not Available!", Toast.LENGTH_SHORT);
                select.show();
            }
            else {

                Intent intent=new Intent(OnlineNeedBlood.this,DisplayListView.class);
                intent.putExtra("json_data",json_string);
                startActivity(intent);
            }
        }

    }


}
