package com.example.expirymate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText pnametxt, qtytxt, expdatetxt, mfgdatetxt, descriptiontxt;
    private String productid, pname, qty, expdate, mfgdate, description, ptype, userid, phnum;
    private Button newbtn, gobackbtn, MfgDateBtn, ExpDateBtn;
    private String[] products = {"EyeBrow", "LipStick", "Powder", "Cream", "Others"};
    private ImageView image;
    private Spinner spinner;
    private int mYear, mMonth, mDay;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        pnametxt = (EditText) findViewById(R.id.editTextproductname);
        qtytxt = (EditText) findViewById(R.id.editTextqty);
        mfgdatetxt = (EditText) findViewById(R.id.editmfgdateproduct);
        expdatetxt = (EditText) findViewById(R.id.editexpdateproduct);
        descriptiontxt = (EditText) findViewById(R.id.editDescription);
        gobackbtn = (Button) findViewById(R.id.gobackbutton);
        newbtn = (Button) findViewById(R.id.newuserbutton);
        MfgDateBtn = (Button) findViewById(R.id.MfgDateBtn);
        ExpDateBtn = (Button) findViewById(R.id.ExpDateBtn);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        phnum = intent.getStringExtra("phnum");
        Log.d("User Id : ", userid);

        MfgDateBtn.setOnClickListener(this);
        ExpDateBtn.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ptype = products[position];
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
                ptype = products[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pname = pnametxt.getText().toString();
                    qty = qtytxt.getText().toString();
                    expdate = expdatetxt.getText().toString();
                    mfgdate = mfgdatetxt.getText().toString();
                    //phnum = phnumtxt.getText().toString();
                    description = descriptiontxt.getText().toString();

                    if (TextUtils.isEmpty(pname)) {
                        pnametxt.setError("Product is Empty");
                        pnametxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(ptype)) {
                        //spinner.setError("ProductType is Empty");
                        spinner.setFocusable(true);
                    } else if (TextUtils.isEmpty(qty)) {
                        qtytxt.setError("Quantity is Empty");
                        qtytxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(description)) {
                        descriptiontxt.setError("Description is Empty");
                        descriptiontxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(mfgdate)) {
                        mfgdatetxt.setError("Manufacture Date is Empty");
                        mfgdatetxt.setFocusable(true);
                    } else if (TextUtils.isEmpty(expdate)) {
                        expdatetxt.setError("Expiry Date is Empty");
                        expdatetxt.setFocusable(true);
                    }
                    //Email validation is done
                    else {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date d1 = formatter.parse(mfgdate);
                        Date d2 = formatter.parse(expdate);

                        if (d2.after(d1)) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //DatabaseReference dbRef = database.getReference();
                            DatabaseReference dbRef = database.getReference();
                            dbRef = database.getReference("Products");
                            productid = dbRef.push().getKey();

                            NewProduct newProduct = new NewProduct(productid, pname, ptype, qty, mfgdate, expdate,
                                    description, userid, phnum);
                            dbRef.child(productid).setValue(newProduct);
                            Toast.makeText(NewProductActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                            //customeridtxt.setText("");
                            pnametxt.setText("");
                            qtytxt.setText("");
                            descriptiontxt.setText("");
                        } else {
                            Toast.makeText(NewProductActivity.this, "Expiry Date Should be greater than Manufacture Date", Toast.LENGTH_LONG).show();
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