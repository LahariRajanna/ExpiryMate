package com.example.expirymate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserMainActivity extends AppCompatActivity {

    private Button newproductbtn, newmedicinebtn, viewproductbtn, viewmedicinebtn, gobackbtn;
    private String userid, phnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        newproductbtn = (Button) findViewById(R.id.newproductbutton);
        newmedicinebtn = (Button) findViewById(R.id.newmedicinebutton);
        viewmedicinebtn = (Button) findViewById(R.id.viewmedicinebutton);
        viewproductbtn = (Button) findViewById(R.id.viewproductbutton);
        gobackbtn = (Button) findViewById(R.id.gobackbutton);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        phnum = intent.getStringExtra("phnum");
        Log.d("User Id : ", userid);

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(send);
            }
        });

        viewproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(),UserProductViewActivity.class);
                send.putExtra("userid", userid);
                send.putExtra("phnum", phnum);
                startActivity(send);
            }
        });

        viewmedicinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(),UserMedicineViewActivity.class);
                send.putExtra("userid", userid);
                send.putExtra("phnum", phnum);
                startActivity(send);
            }
        });

        newproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(),NewProductActivity.class);
                send.putExtra("userid", userid);
                send.putExtra("phnum", phnum);
                startActivity(send);
            }
        });

        newmedicinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(),NewMedicineActivity.class);
                send.putExtra("userid", userid);
                send.putExtra("phnum", phnum);
                startActivity(send);
            }
        });
    }
}