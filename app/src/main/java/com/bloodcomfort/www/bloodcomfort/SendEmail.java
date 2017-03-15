package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.Session;

public class SendEmail extends Activity implements View.OnClickListener{
    TextView txt;
    public String message;
    TextView textView8;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText name, email1, phone,description,donorname,donormobile;
    String  nametsr, emailstr, phonestr, subject, descriptionstr, donor_email,donor_mobile;
    private Button buttonSend;
    public String email,mobile1;

    private static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        mobile1 = intent.getStringExtra("mobile");



        name = (EditText) findViewById(R.id.ET_name);
        email1 = (EditText) findViewById(R.id.ET_email);
        phone = (EditText) findViewById(R.id.ET_number);
        description=(EditText)findViewById(R.id.editText);
        donorname=(EditText)findViewById(R.id.editText2);
        donormobile=(EditText)findViewById(R.id.editText3);


        buttonSend = (Button) this.findViewById(R.id.button3);

        buttonSend.setOnClickListener(this);

    }

    public void GMailSender() {

        nametsr = name.getText().toString();
        emailstr = email1.getText().toString();
        phonestr = phone.getText().toString();
        descriptionstr=description.getText().toString();
        String text="Dear User,";
        String text1="       A user on bloodcomfort is in an emergency and needs you to donate blood.\nFollowing are the user's contact details: ";
        String link="https://play.google.com/store/apps/details?id=com.bloodcomfort.www.bloodcomfort";

        StringBuilder s = new StringBuilder(100);
        s.append(text);
        s.append("\n");
        s.append(text1);
        s.append("\n");
        s.append(nametsr);
        s.append("\n");
        s.append(emailstr);
        s.append("\n");
        s.append(phonestr);
        s.append("\n");
        s.append(descriptionstr);
        s.append("\n");
        s.append(link);
        subject="Someone Needs Your Help!!!";
        //Creating SendMail object
        GMailSender sm = new GMailSender(this, email, subject, s);

        //Executing sendmail to send email
        sm.execute();

        donorname.setText(email);
        donormobile.setText(mobile1);
    }

    @Override
    public void onClick(View v) {

        nametsr = name.getText().toString();
        emailstr = email1.getText().toString();
        phonestr = phone.getText().toString();
        descriptionstr=description.getText().toString();

        if (nametsr.equals("")) {
            Toast name = Toast.makeText(SendEmail.this, "Name field is Empty!", Toast.LENGTH_SHORT);
            name.show();
        } else if (emailstr.equals("")) {
            Toast email = Toast.makeText(SendEmail.this, "Email field is Empty!", Toast.LENGTH_SHORT);
            email.show();

        } else if (phonestr.equals("")) {
            Toast uname = Toast.makeText(SendEmail.this, "Contact field is Empty!", Toast.LENGTH_SHORT);
            uname.show();
        }
        else if(!nametsr.equals("") && !emailstr.equals("") && !phonestr.equals("")){


            if (!TextUtils.isEmpty(emailstr) && Utility.validate(emailstr) && Utility.isValidPhoneNumber(phonestr)) {

                GMailSender();


            } else {
                Toast.makeText(SendEmail.this, "Please enter valid email",
                        Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast password = Toast.makeText(SendEmail.this, "Not Succesfull!", Toast.LENGTH_SHORT);
            password.show();
        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("facebookname1", "");
            String restoredText1 = prefs.getString("facebookemail1", "");
            if(restoredText!=null) {
                Intent a = new Intent(this, OnlineNeedBlood.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                this.finish();
                return true;
            }else {
                Intent a = new Intent(this, OnlineNeedBlood.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}