package com.bloodcomfort.www.bloodcomfort;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdView;


public class Display1 extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "MyPrefsFile";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public String face_name,face_email;
    SharedPreferences sharedPref1;
    SharedPreferences.Editor online_session;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        pref = getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();

        AdView adView = (AdView)findViewById(R.id.adView);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        adView.loadAd(adRequest);


         face_name = getIntent().getStringExtra("face_name");
         face_email = getIntent().getStringExtra("face_email");



        ActionBar actionBar=getActionBar();
        // actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.activity_display,menu);

        // MenuItem item = menu.findItem(R.id.action_share);
        //  mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_id:
                editor.putString("register", "false");
                editor.commit();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(Display1.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "log Out Successfull", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =
                        "https://play.google.com/store/apps/details?id=com.bloodcomfort.www.bloodcomfort \n\n" +
                                "Check out blood comfort on play store Now!\n" +
                                "An app which helps you meet blood donors in your city and also allows you to volunteer for blood donation!\n" +
                                "Cheers!\n" +
                                "Developers \n" +
                                "Ajinkya Ghadge\n" +
                                "Sagar Annaldas";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blood Comfort-Android Apps on Google Play");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;

            case R.id.howto:
                Intent intent = new Intent(Display1.this, Howto.class);
                startActivity(intent);
                return true;

            case R.id.aboutButton:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bloodcomfort.com"));
                startActivity(browserIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

        //noinspection SimplifiableIfStatement
    }



    public void onButtonClick(View v) {
        if (v.getId() == R.id.button) {
            // Bundle online_session = getIntent().getExtras();

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("facebookname1", "");
            String restoredText1 = prefs.getString("facebookemail1", "");
          //  Intent intent = getIntent();
         //   String name = intent.getStringExtra("name1");
         //   String email = intent.getStringExtra("email1");

            if (restoredText!=null) {

                Intent i = new Intent(Display1.this, OnlineDonateBlood.class);
                i.putExtra("face_name", restoredText);
                i.putExtra("face_email", restoredText1);
                startActivity(i);
            }

        }
        if (v.getId() == R.id.button2) {
            //  Bundle online_session = getIntent().getExtras();

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("facebookname1", "");
            String restoredText1 = prefs.getString("facebookemail1", "");
          //  Intent intent = getIntent();
         //   String name = intent.getStringExtra("name1");
          //  String email = intent.getStringExtra("email1");

            if (restoredText!=null) {
                Intent i = new Intent(Display1.this, OnlineNeedBlood.class);
                i.putExtra("face_name", restoredText);
               i.putExtra("face_email", restoredText1);
                startActivity(i);
            }

        }
      /*  if(v.getId()==R.id.LObutton){
            Intent i = new Intent(Display.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
            startActivity(i);
            finish();
            Toast alert = Toast.makeText(Display.this,"Log Out Successfull!" ,Toast.LENGTH_SHORT);
            alert.show();

        }*/

    }

    /*
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


      private static long back_pressed;

      @Override
      public void onBackPressed()
      {
          if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
          else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
          back_pressed = System.currentTimeMillis();


      }
      */
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);// finish activity
        } else {
            Toast.makeText(this, "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);

        }

    }

}
