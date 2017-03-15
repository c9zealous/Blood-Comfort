package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pushbots.push.Pushbots;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends ActionBarActivity {
    EditText ET_NAME,ET_PASS;
    String login_pass;
    TextView textView3;
    Button userLogin,userReg;
    public String login_name,id1,name1,email1;
    EditText name,email,uname,pass1,pass2;
    String namestr,emailstr,unamestr,pass1str,pass2str;

    private static final String MY_PREFS_NAME = "MyPrefsFile";

    private TextView info;
     LoginButton loginButton;
     CallbackManager callbackManager;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        callbackManager = CallbackManager.Factory.create();

        pref = getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bloodcomfort.www.bloodcomfort",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {
                        response.getError();
                        Log.e("JSON:", object.toString());
                      // JSONObject json = response.getJSONObject();
                        try {
                                 id1 = object.getString("id");
                                 name1 = object.getString("name");
                                 email1 = object.getString("email");

                                Log.d("id", id1);
                                Log.d("name", name1);
                                Log.d("email", email1);

                            Pushbots.sharedInstance().setAlias(name1);

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("facebookname1", name1);
                            editor.putString("facebookemail1", email1);
                            editor.putString("key","true");
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
  /*          @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.v("sagar", response.toString());
                            }
                        });

               Bundle parameters = new Bundle();
                parameters.putString("id", "id");
                parameters.putString("name", "name");
                parameters.putString("email", "email");
                request.setParameters(parameters);
                request.executeAsync();
                editor.putString("register", "false1");
                editor.commit();

*/
                editor.putString("register", "false1");
                editor.commit();
                if (parameters == null ) {
                    // Kill login activity and go back to mail
                    editor.putString("id", id1);
                    Intent i = new Intent(MainActivity.this, Display.class);
                    startActivity(i);

                }else{

                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    String restoredText = prefs.getString("facebookname1", "");
                    String restoredText1 = prefs.getString("facebookemail1", "");

                    Intent intent = new Intent(getApplicationContext(), Display1.class);
                    intent.putExtra("face_name", restoredText);
                    intent.putExtra("face_email", restoredText1);
                    startActivity(intent);
                }

                /*
                if (parameters != null ) {
                    // Kill login activity and go back to main
                    Intent intent = new Intent(getApplicationContext(), Display.class);
                    intent.putExtra("online_session", parameters);
                    startActivity(intent);
                }else {

                    Intent i = new Intent(MainActivity.this, Display.class);
                    startActivity(i);
                }
                */

            }

            @Override
            public void onCancel() {
                // App code
                Log.v("sagar", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("sagar", exception.getCause().toString());
            }
        });


        String getStatus=pref.getString("register", "");
        if(getStatus.equals("true")) {
            Intent launchActivity1= new Intent(MainActivity.this,Display.class);
            startActivity(launchActivity1);
            MainActivity.this.finish();
        }else if(getStatus.equals("false1")) {
            Intent launchActivity1= new Intent(MainActivity.this,Display1.class);
            startActivity(launchActivity1);
            MainActivity.this.finish();
        }



        ET_NAME=(EditText)findViewById(R.id.user_name);
        ET_PASS=(EditText)findViewById(R.id.user_pass);

        name =(EditText)findViewById(R.id.TFname);
        email =(EditText)findViewById(R.id.TFemail);

        pass1 =(EditText)findViewById(R.id.TFpass1);
        pass2 =(EditText)findViewById(R.id.TFpass2);

        login_name=ET_NAME.getText().toString();

        Pushbots.sharedInstance().init(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void userReg(View view){
        startActivity(new Intent(this,SignUp.class));

    }

    public void userLogin(View view){
        login_name=ET_NAME.getText().toString();
        login_pass=ET_PASS.getText().toString();
        String method="login";


            BackgroundTask backgroundTask =new BackgroundTask(this);
            backgroundTask.execute(method, login_name, login_pass);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login_name1", login_name);
        editor.commit();

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
            String login_url ="http://www.bloodcomfort.com/login.php";

            String login_name=params[1];
            String login_pass=params[2];

            try {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("login_name", "UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");

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
            if (result.equals("Login Success welcome")) {
                editor.putString("register","true");
                editor.commit();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("key","false");
                editor.commit();

                Pushbots.sharedInstance().setAlias(login_name);
                  Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, Display.class);
                startActivity(i);
            }else  {

                alertDialog.setMessage(result);
                alertDialog.show();
            }

        }

    }



    private static long back_pressed;

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

}
