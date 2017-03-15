package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Howto extends Activity{

    TextView textView9,textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);


        String data=
                "Welcome to the BloodComfort community,\n" +
                        "\n" +
                        "BloodComfort has a vision towards providing connectivity for the people who need blood with available donors in your city! Blood happens to be the most important fluid which all of us need to survive, however it becomes a huge problem to find donors in case of emergency and therefore we at BloodComfort work towards providing easy connectivity between the donees and the donors. We've tried to keep the interface extremely simple inorder to make it accessible to anyone and everyone.\n" +
                        "\n" +
                        "How to Use Blood Comfort?\n" +
                        "\n" +
                        "BloodComfort is fairly simple to use! \n" +
                        "\n" +
                        "1) When you open the application you need to register and you can do that using the Sign-Up button or if you're on Facebook you can simply Log-in with Facebook.\n" +
                        "2) After signing up enter your credentials such as username, location and phone number etc.\n" +
                        "3) Once signed-up you can then select whether you need blood? or you want to donate blood!\n" +
                        "4) If you want to donate blood, hit the button and select your blood type and you'll be registered with us as a Donor. Any person who is in emergency can then contact you via email or phone. Dont worry, you will get the donees details in your email id as well.\n" +
                        "5) If you need blood then hit the button and select the blood type you need. Depending on the bloodtype you selected you will see a list of donors in your city. You can then select a user and send the donor an email including your contact details and customised message. You can see the details of the donor once you send them the email.\n" +
                        "6) Thats all and Dont forget to share us on Facebook! \n" +
                        "\n" +
                        "Cheers!\n" +
                        "Feel free to share your thoughts with us on,\n" +
                        "\n" +
                        "bloodcomfortgroup@gmail.com";


        TextView t = (TextView) findViewById(R.id.textView10);
        t.setText(data);

    }
}
