package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

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


public class NeedBlood extends ActionBarActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    public String blood_type,login_name;
    String  json_string;

    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needblood);
        spinner=(Spinner)findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this, R.array.blood_type_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdView adView = (AdView)findViewById(R.id.adView);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        adView.loadAd(adRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood_type = spinner.getSelectedItem().toString();

                //   Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void needButton(View view){


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        login_name = settings.getString("login_name1", "");

        String method ="need";
        BackgroundTask backgroundTask =new BackgroundTask(this);
        backgroundTask.execute(method, blood_type, login_name);

        if(json_string==null){
            Toast select = Toast.makeText(NeedBlood.this, "Loading Page...!!!", Toast.LENGTH_LONG);
            select.show();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
               if(json_string.equals("{\"server_response\":[]}")){
                    Toast select = Toast.makeText(NeedBlood.this, "Selected Blood Type Is Not Available!", Toast.LENGTH_SHORT);
                    select.show();
                }
                else if(json_string!=null) {

                    Intent intent=new Intent(NeedBlood.this,DisplayListView.class);
                    intent.putExtra("json_data",json_string);
                    startActivity(intent);
                }
            }
        }, 5000);
    }


    class BackgroundTask extends AsyncTask<String,Void,String> {

        Context ctx;
        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String login_url ="http://www.bloodcomfort.com/need.php";

            String blood_type=params[1];
            String login_name=params[2];

            try {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("login_name", "UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("blood_type", "UTF-8")+"="+URLEncoder.encode(blood_type,"UTF-8");


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
        }

    }




}

