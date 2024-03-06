package com.example.expirymate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerLoginActivity extends AppCompatActivity {

    private Button backbtn, loginbtn;
    private EditText txtuname, txtpwd;
    private String uname, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        backbtn=(Button)findViewById(R.id.backbutton);

        txtuname = (EditText)findViewById(R.id.editTextusername);
        txtpwd = (EditText)findViewById(R.id.editTextpassword);
        loginbtn=(Button) findViewById(R.id.buttonlogin);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //txtuname.setText("lahari01");
        //txtpwd.setText("lahari");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = txtuname.getText().toString();
                pwd = txtpwd.getText().toString();

                if(TextUtils.isEmpty(uname))
                {
                    txtuname.setError("UserName is Empty");
                    txtuname.setFocusable(true);
                }
                else if(TextUtils.isEmpty(pwd))
                {
                    txtpwd.setError("Password is Empty");
                    txtpwd.setFocusable(true);
                }
                else
                {
                    //Adding addValueEventListener method on firebase object.
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Customer");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String str = "";
                            boolean flag=false;
                            String userid=null, phnum=null;
                            for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                                NewUser customer = SubSnapshot.getValue(NewUser.class);
                                String password = customer.getPassword();
                                String username = customer.getUsername();
                                userid=customer.getCustomerid();
                                phnum=customer.getPhonenum();
                                Log.d("User id : ", userid);
                                // Adding name and phone number of student into string that is coming from server.
                                if(uname.equals(uname) && pwd.equals(password))
                                {
                                    flag=true;
                                    break;
                                }
                            }
                            if(flag==true)
                            {
                                Toast.makeText(getApplicationContext(), "Customer Login Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                intent.putExtra("userid", userid);
                                intent.putExtra("phnum", phnum);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Invalid UserName/Password", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Data Access Failed" + error.getMessage());
                        }
                    });
                }
            }
        });
    }
}