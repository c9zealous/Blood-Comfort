package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class LoadingScreen extends Activity {

    //Introduce an delay
    private final int WAIT_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("LoadingScreenActivity  screen started");
        setContentView(R.layout.loadingscreen);
        findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Simulating a long running task
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Searching Selected Blood Type");
	  /* Create an Intent that will start the ProfileData-Activity. */
                Intent mainIntent = new Intent(LoadingScreen.this,DisplayListView.class);
                mainIntent.putExtra("json_data","");
                LoadingScreen.this.startActivity(mainIntent);
                LoadingScreen.this.finish();
            }
        }, WAIT_TIME);
    }
}

