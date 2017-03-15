package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class DonateBlood extends ActionBarActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    public String blood_type,login_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donateblood);
        spinner=(Spinner)findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.blood_type_names,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdView adView = (AdView)findViewById(R.id.adView);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        adView.loadAd(adRequest);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                blood_type=spinner.getSelectedItem().toString();
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void donateButton(View view) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        login_name = settings.getString("login_name1", "");

        String method ="donate";
        BackgroundTask backgroundTask =new BackgroundTask(this);
        backgroundTask.execute(method, blood_type,login_name);

        Intent intent=new Intent(this,ThankYou.class);
        intent.putExtra("json_data",login_name);
        startActivity(intent);

    }


    class BackgroundTask extends AsyncTask<String,Void,String> {

        AlertDialog alertDialog;
        Context ctx;
        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String login_url ="http://www.bloodcomfort.com/donate.php";

            String blood_type=params[1];
            String login_name=params[2];

            try {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
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
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response ="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){

                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;


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
            if (result.equals("Thank You...")) {
                Toast.makeText(DonateBlood.this,"Thank You..." ,Toast.LENGTH_LONG).show();

            }else  {
                Toast.makeText(DonateBlood.this,"Username already registered with a bloodtype" ,Toast.LENGTH_SHORT).show();
            }

        }

    }

}
