package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

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
import java.util.StringTokenizer;


public class SignUp extends ActionBarActivity {


    Spinner sp,sp1,sp2;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayList<String> listItems1=new ArrayList<>();
    ArrayList<String> listItems2=new ArrayList<>();
    public   String Country_value,country_id1,State_value,state_id1;
    int country_id,state_id;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    EditText name,email,uname,pass1,pass2,phoneNumber;
    String namestr,emailstr,unamestr,pass1str,pass2str,phonestr;

    public String country,state,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        sp=(Spinner)findViewById(R.id.spinner);
        sp1=(Spinner)findViewById(R.id.spinner1);
        sp2=(Spinner)findViewById(R.id.spinner2);

        adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        adapter1=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems1);
        adapter2=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems2);

        sp.setAdapter(adapter);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2);

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

                state_id1 = String.valueOf(state_id+1);


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


        name =(EditText)findViewById(R.id.TFname);
        email =(EditText)findViewById(R.id.TFemail);

        pass1 =(EditText)findViewById(R.id.TFpass1);
        pass2 =(EditText)findViewById(R.id.TFpass2);
        phoneNumber=(EditText)findViewById(R.id.phone);


    }

    public void onStart(){
        super.onStart();

        new CountryValue().execute();
    }

private class CountryValue extends AsyncTask<Void,Void,Void> {
    ArrayList<String> list;
    protected void onPreExecute(){
        super.onPreExecute();
        list=new ArrayList<>();
    }
    protected Void doInBackground(Void...params){
        String login_url1 ="http://www.bloodcomfort.com/spinner_country.php";
        InputStream is=null;
        String result="";
        try{

            URL url =new URL(login_url1);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response ="";
            String line="";
            while ((line=bufferedReader.readLine())!=null){

                response+=line;
            }

            try{
                JSONArray jArray =new JSONArray(response);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    StringBuilder s1 = new StringBuilder(100);
                   // s1.append(jsonObject.getString("id"));
                    s1.append(jsonObject.getString("name"));

                    list.add(String.valueOf(s1));
                }
            }
            catch(JSONException e){
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
    protected void onPostExecute(Void result){
        listItems.addAll(list);
        adapter.notifyDataSetChanged();

    }
}

class BackgroundTask1 extends AsyncTask<String,Void,String> {
    ArrayList<String> list1;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list1=new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url2 ="http://www.bloodcomfort.com/spinner_state.php";
        String response="";
        String id=params[1];
        String login_pass=params[2];

        try {
            URL url=new URL(login_url2);
            HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line="";
            while ((line=bufferedReader.readLine())!=null){

                response+=line;
            }

            try{
                JSONArray jArray =new JSONArray(response);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
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

class BackgroundTask2 extends AsyncTask<String,Void,String> {
    ArrayList<String> list2;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list2=new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url ="http://www.bloodcomfort.com/spinner_city.php";
        String response="";
        String id=params[1];
        String login_pass=params[2];

        try {
            URL url=new URL(login_url);
            HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line="";
            while ((line=bufferedReader.readLine())!=null){

                response+=line;
            }

            try{
                JSONArray jArray =new JSONArray(response);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
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


    public void userReg(View view) {

        namestr = name.getText().toString();
        emailstr = email.getText().toString();

        pass1str = pass1.getText().toString();
        pass2str = pass2.getText().toString();
        phonestr=phoneNumber.getText().toString();


        String method = "register";




        if (!pass1str.equals(pass2str)) {
            //popup message
            Toast pass = Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT);
            pass.show();
        } else if (namestr.equals("")) {
            Toast name = Toast.makeText(SignUp.this, "Name field is Empty!", Toast.LENGTH_SHORT);
            name.show();
        } else if (emailstr.equals("")) {
            Toast email = Toast.makeText(SignUp.this, "Email field is Empty!", Toast.LENGTH_SHORT);
            email.show();

        }  else if (pass1str.equals("") ) {
            Toast password = Toast.makeText(SignUp.this, "Passwords Field is Empty!", Toast.LENGTH_SHORT);
            password.show();
        }else if (phonestr.equals("") ) {
            Toast phone = Toast.makeText(SignUp.this, "Phone Field is Empty!", Toast.LENGTH_SHORT);
            phone.show();

        }
        else if(!namestr.equals("") && !emailstr.equals("")  && !pass1str.equals("")){





            if (!TextUtils.isEmpty(emailstr) && Utility.validate(emailstr) && Utility.isValidPhoneNumber(phonestr)) {

                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(method, namestr, emailstr,phonestr,country,state,city,pass1str);
                finish();


            } else {
                Toast.makeText(SignUp.this, "Please enter valid email",
                        Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast password = Toast.makeText(SignUp.this, "Not Succesfull!", Toast.LENGTH_SHORT);
            password.show();
        }
    }






    class BackgroundTask extends AsyncTask<String,Void,String> {

        AlertDialog alertDialog;
        Context ctx;
        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(("Login Information..."));
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url ="http://www.bloodcomfort.com/register.php";

            String method = params[0];
            if(method.equals("register")){
                String namestr = params[1];
                String emailstr = params[2];
                String phonestr = params[3];
                String country = params[4];
                String state = params[5];
                String city = params[6];
                String pass1str = params[7];
                try {

                    URL url =new URL(reg_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    OutputStream OS =httpURLConnection.getOutputStream();


                    BufferedWriter bufferWriter =new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data= URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(namestr,"UTF-8") + "&" +
                            URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(emailstr,"UTF-8") + "&" +
                            URLEncoder.encode("mobile","UTF-8") + "=" + URLEncoder.encode(phonestr,"UTF-8")+ "&" +
                            URLEncoder.encode("country","UTF-8") + "=" + URLEncoder.encode(country,"UTF-8") + "&" +
                            URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode(state,"UTF-8") + "&" +
                            URLEncoder.encode("city","UTF-8") + "=" + URLEncoder.encode(city,"UTF-8") + "&" +
                            URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(pass1str,"UTF-8") ;

                    bufferWriter.write(data);
                    bufferWriter.flush();
                    bufferWriter.close();
                    OS.close();

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

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Data insertion error...")) {

                Toast alert = Toast.makeText(SignUp.this,"Already Registered With This Email-Id " ,Toast.LENGTH_SHORT);
                alert.show();
            }else if(result.equals("Data Insertion Success...")){
                Toast alert = Toast.makeText(SignUp.this,"Sign Up Successfull..." ,Toast.LENGTH_SHORT);
                alert.show();
            }

        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
