package com.example.expirymate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {

    private Button adminloginbtn, userloginbtn, newregbtn, gobackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        userloginbtn = (Button) findViewById(R.id.userloginbutton);
        newregbtn = (Button) findViewById(R.id.newuserbutton);
        gobackbtn = (Button) findViewById(R.id.gobackbutton);



        userloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
                Intent send = new Intent(getApplicationContext(),CustomerLoginActivity.class);
                startActivity(send);
            }
        });

        newregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
                Intent send = new Intent(getApplicationContext(),NewUserActivity.class);
                startActivity(send);
            }
        });

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
                Intent send = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(send);
            }
        });
    }
}