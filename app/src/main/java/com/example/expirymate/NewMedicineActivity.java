package com.example.expirymate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewMedicineActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mnametxt, qtytxt, expdatetxt, mfgdatetxt, descriptiontxt;
    private String medicineid, mname, qty, expdate, mfgdate, description, mtype, userid, phnum;
    private Button newbtn, gobackbtn, MfgDateBtn, ExpDateBtn;
    private String[] products = {"Syrups", "Tablets", "Powder", "Ointments", "Others"};
    private int mYear, mMonth, mDay;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        mnametxt = (EditText) findViewById(R.id.editTextmedicinename);
        qtytxt = (EditText) findViewById(R.id.editTextqty);
        expdatetxt = (EditText) findViewById(R.id.editexpdatemedicine);
        mfgdatetxt = (EditText) findViewById(R.id.editmfgdatemedicine);
        qtytxt = (EditText) findViewById(R.id.editTextqty);
        descriptiontxt = (EditText) findViewById(R.id.editDescription);
        gobackbtn = (Button) findViewById(R.id.gobackbutton);
        newbtn = (Button) findViewById(R.id.newuserbutton);
        MfgDateBtn = (Button) findViewById(R.id.MfgDateBtn);
        ExpDateBtn = (Button) findViewById(R.id.ExpDateBtn);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        phnum = intent.getStringExtra("phnum");

        MfgDateBtn.setOnClickListener(this);
        ExpDateBtn.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype = products[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, products);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype = products[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mname = mnametxt.getText().toString();
                    qty = qtytxt.getText().toString();
                    expdate = expdatetxt.getText().toString();
                    mfgdate = mfgdatetxt.getText().toString();
                    description = descriptiontxt.getText().toString();

                    if (TextUtils.isEmpty(mname)) {
                        mnametxt.setError("Medicine is Empty");
                        mnametxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(mtype)) {
                        //spinner.setError("ProductType is Empty");
                        spinner.setFocusable(true);
                    } else if (TextUtils.isEmpty(qty)) {
                        qtytxt.setError("Quantity is Empty");
                        qtytxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(description)) {
                        descriptiontxt.setError("Description is Empty");
                        descriptiontxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(expdate)) {
                        expdatetxt.setError("Expiry Date is Empty");
                        expdatetxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(mfgdate)) {
                        mfgdatetxt.setError("EmailId is Empty");
                        mfgdatetxt.setFocusable(true);
                    } else {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date d1 = formatter.parse(mfgdate);
                        Date d2 = formatter.parse(expdate);
                        Log.d("MfgDate : ", mfgdate);
                        Log.d("ExpDate : ", expdate);
                        if (d2.after(d1)) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //DatabaseReference dbRef = database.getReference();
                            DatabaseReference dbRef = database.getReference();
                            dbRef = database.getReference("Medicines");
                            medicineid = dbRef.push().getKey();
                            //dbRef.child("Student").child(StudentId).setValue(student);
                            //dbRef.child(StudentId).setValue(student);
                            NewMedicine newMedicine = new NewMedicine(medicineid, mname, mtype, qty, mfgdate, expdate,
                                    description, userid, phnum);
                            dbRef.child(medicineid).setValue(newMedicine);
                            Toast.makeText(NewMedicineActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                            //customeridtxt.setText("");
                            mnametxt.setText("");
                            qtytxt.setText("");
                            descriptiontxt.setText("");
                            mfgdatetxt.setText("");
                            expdatetxt.setText("");
                        } else {
                            Toast.makeText(NewMedicineActivity.this, "Expiry Date Should be greater than Manufacture Date", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Log.d("Exception : ", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == MfgDateBtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            c.add(Calendar.DATE, 730);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mfgdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -1460);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        } else if (v == ExpDateBtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            c.add(Calendar.DATE, 730);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            expdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -1460);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
    }
}