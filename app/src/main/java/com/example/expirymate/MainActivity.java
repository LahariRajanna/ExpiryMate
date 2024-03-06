package com.example.expirymate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button openbtn, exitbtn, browsebtn;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitbtn = (Button) findViewById(R.id.btnExit);
        openbtn = (Button) findViewById(R.id.btnClick);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = "";
                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                    NewProduct product = SubSnapshot.getValue(NewProduct.class);
                    String productId = SubSnapshot.getKey();
                    productId = product.getProductid();
                    String ShowDataString = "Id : " + productId + "\nProduct Name : " + product.getProductname() +
                            "\nProduct Type : " + product.getProducttype() + "\nQuantity : " + product.getQty() +
                            "\nMfgDate : " + product.getMfgdate() + "\nExpDate : " + product.getExpdate() +
                            "\nDescription : " + product.getDescription();

                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date d1 = formatter.parse(product.getExpdate());
                        Date date = new Date();
                        String s1 = date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
                        Date d2 = formatter.parse(s1);
                        System.out.println("D1 : " + d1);
                        System.out.println("D2 : " + d2);
                        long difference_In_Time
                                = d1.getTime() - d2.getTime();
                        // Calucalte time difference in
                        // seconds, minutes, hours, years,
                        // and days
                        long difference_In_Seconds
                                = (difference_In_Time
                                / 1000)
                                % 60;

                        long difference_In_Minutes
                                = (difference_In_Time
                                / (1000 * 60))
                                % 60;

                        long difference_In_Hours
                                = (difference_In_Time
                                / (1000 * 60 * 60))
                                % 24;

                        long difference_In_Years
                                = (difference_In_Time
                                / (1000l * 60 * 60 * 24 * 365));

                        long difference_In_Days
                                = (difference_In_Time
                                / (1000 * 60 * 60 * 24))
                                % 365;

                        Log.d("Diff in days : ", String.valueOf(difference_In_Days));

                        System.out.println("Days : " + difference_In_Days);

                        if (difference_In_Days == 1) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Day Reminder : Your Product " + product.getProductname() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        } else if (difference_In_Days <= 7) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Week Reminder : Your Product " + product.getProductname() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        } else if (difference_In_Days <= 30) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Month Reminder : Your Product " + product.getProductname() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Exception : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Data Access Failed" + error.getMessage());
            }
        });


        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("Medicines");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = "";
                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                    NewMedicine product = SubSnapshot.getValue(NewMedicine.class);
                    String productId = SubSnapshot.getKey();
                    productId = product.getMedicineid();
                    // Adding name and phone number of student into string that is coming from server.

                    String ShowDataString = "Id : " + productId + "\nMedicine Name : " + product.getMedicinename() +
                            "\nMedicine Type : " + product.getMedicinetype() + "\nQuantity : " + product.getQty() +
                            "\nMfgDate : " + product.getMfrdate() + "\nExpiry Date : " + product.getExpdate() +
                            "\nDescription : " + product.getDescription();

                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date d1 = formatter.parse(product.getExpdate());
                        Date d2 = new Date();
                        long difference_In_Time
                                = d1.getTime() - d2.getTime();
                        // Calucalte time difference in
                        // seconds, minutes, hours, years,
                        // and days
                        long difference_In_Seconds
                                = (difference_In_Time
                                / 1000)
                                % 60;

                        long difference_In_Minutes
                                = (difference_In_Time
                                / (1000 * 60))
                                % 60;

                        long difference_In_Hours
                                = (difference_In_Time
                                / (1000 * 60 * 60))
                                % 24;

                        long difference_In_Years
                                = (difference_In_Time
                                / (1000l * 60 * 60 * 24 * 365));

                        long difference_In_Days
                                = (difference_In_Time
                                / (1000 * 60 * 60 * 24))
                                % 365;

                        Log.d("Diff in days : ", String.valueOf(difference_In_Days));

                        System.out.println("Days : " + difference_In_Days);

                        if (difference_In_Days == 1) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Day Reminder : Your Medicine " + product.getMedicinename() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        } else if (difference_In_Days <= 7) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Week Reminder : Your Medicine " + product.getMedicinename() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        } else if (difference_In_Days <= 30) {
                            String phone_Num = product.getPhnum();
                            String send_msg = "Month Reminder : Your Medicine " + product.getMedicinename() + " will expired on " + product.getExpdate();
                            sendsms(phone_Num, send_msg);
                            //SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                            //SmsManager smsManager = SmsManager.getDefault();
                            //ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[] { Manifest.permission.SEND_SMS}, 1);
                            //smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Exception : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Data Access Failed" + error.getMessage());
            }
        });

        /*try {
            String phone_Num = "9986566655";
            String send_msg = "Hi";
            SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text

            SmsManager smsManager = SmsManager.getDefault();
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.SEND_SMS}, 1);
            smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);

            //sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
            Toast.makeText(this, "Sms Sent Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Sms Not Send", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/

        /*
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        //Get the SmsManager instance and call the sendTextMessage method to send message

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {

// Permission is not granted
// Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,"Manifest.permission.READ_SMS") ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,"Manifest.permission.READ_SMS")) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{"Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS"},
                        1);

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        else {
            // Permission has already been granted
        }

        //SmsManager sms=SmsManager.getDefault();
        //sms.sendTextMessage("9886239083", null, "hello javatpoint", null,null);

        String mblNumVar="9886239083";
        String smsMsgVar="hello javatpoint";
        Log.d("SEND SMS", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)));
        Log.d("PERMISSION", String.valueOf(PackageManager.PERMISSION_GRANTED));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception ErrVar)
            {
                Toast.makeText(getApplicationContext(),ErrVar.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }

        //sendSMS(mblNumVar, smsMsgVar);
        sendSMSMessage(mblNumVar,smsMsgVar);
        Log.d("SMS","Sent Success");
        */

        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(), MainPageActivity.class);
                startActivity(send);
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
            }
        });
    }
    /*
    public void sendSMSMessage(String phoneNumber, String message) {
        ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.SEND_SMS}, 1);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("9886239083", null, "hi", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        //PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        //PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        PendingIntent sentPI = null,deliveredPI=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            //pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
          sentPI=  PendingIntent.getBroadcast(this, 0, new Intent(SENT), PendingIntent.FLAG_MUTABLE);
          deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            //pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            sentPI=  PendingIntent.getBroadcast(this, 0, new Intent(SENT), PendingIntent.FLAG_ONE_SHOT);
            deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), PendingIntent.FLAG_ONE_SHOT);
        }

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:

                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:

                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:

                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }*/

    public void sendsms(String phone_Num, String send_msg) {
        try {
            SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text

            SmsManager smsManager = SmsManager.getDefault();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            smsManager.sendTextMessage(phone_Num, null, send_msg, null, null);

            //sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
            Toast.makeText(this, "Sms Sent Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Sms Not Send", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}