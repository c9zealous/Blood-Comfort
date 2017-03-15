package com.bloodcomfort.www.bloodcomfort;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ThankYou extends Activity {
    TextView textView9,textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);


        String username = getIntent().getStringExtra("json_data");

        StringBuilder s = new StringBuilder(100);
        s.append("\n");
        s.append("Dear ");
        s.append(username);


        TextView tv = (TextView) findViewById(R.id.textView9);
        tv.setText(s);

        String data=
                "Thank you for taking out time from your busy schedule to become a valuable part of the world of donors. You will be notified by a user who needs blood in your city. Your blood could save anyone from a little baby to an old individual.\n" +
                "Blood donation is the act of giving life. One cannot make blood, it is a gift we all have inside of us to give to those who are injured, sick or in need. There's no doubt the need is huge. However, the support of donors like you is helping us to make it possible to help the people in achieving a healthier, happier and fruitful life.\n" +
                "Your effort shows your generosity, selflessness and dedication to helping others. Those are remarkable traits.\n" +
                "Sincere Thanks, from all of us at Blood Comfort.\n\n" +

                " For any queries contact us : bloodcomfortgroup@gmail.com\n";


        TextView t = (TextView) findViewById(R.id.textView10);
        t.setText(data);

    }
}
