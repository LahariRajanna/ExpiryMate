package com.example.expirymate;

import androidx.annotation.NonNull;
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

import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserViewMedicineActivity extends AppCompatActivity implements View.OnClickListener{

    private Button deletebtn, updatebtn, backbtn, MfgDateBtn, ExpDateBtn;;
    private int mYear, mMonth, mDay;
    private EditText mnametxt, qtytxt, expdatetxt, mfgdatetxt, descriptiontxt;
    private String medicineid, mname, qty, expdate, mfgdate, description, mtype, userid, phnum;
    private Spinner spinner;
    private String[] products = {"Syrups", "Tablets", "Powder", "Ointments", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_medicine);

        mnametxt = (EditText) findViewById(R.id.editTextmedicinename);
        qtytxt = (EditText) findViewById(R.id.editTextqty);
        expdatetxt = (EditText) findViewById(R.id.editexpdatemedicine);
        mfgdatetxt = (EditText) findViewById(R.id.editmfgdatemedicine);
        qtytxt = (EditText) findViewById(R.id.editTextqty);
        descriptiontxt = (EditText) findViewById(R.id.editDescription);
        backbtn = (Button) findViewById(R.id.gobackbutton);
        updatebtn = (Button) findViewById(R.id.updateproductbutton);
        deletebtn = (Button) findViewById(R.id.deleteproductbutton);
        MfgDateBtn = (Button) findViewById(R.id.MfgDateBtn);
        ExpDateBtn = (Button) findViewById(R.id.ExpDateBtn);

        MfgDateBtn.setOnClickListener(this);
        ExpDateBtn.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, products);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype = products[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Medicines");
                myRef.child(id).removeValue();
                Toast.makeText(getApplicationContext(), "Medicine Deleted Success", Toast.LENGTH_LONG).show();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Medicines");
                mname = mnametxt.getText().toString();
                qty = qtytxt.getText().toString();
                expdate = expdatetxt.getText().toString();
                mfgdate = mfgdatetxt.getText().toString();
                description = descriptiontxt.getText().toString();

                Log.d("id : ", id);

                if (TextUtils.isEmpty(mname)) {
                    mnametxt.setError("Medicine Name is Empty");
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
                }
                else {
                    myRef.child(id).child("medicinename").setValue(mname);
                    myRef.child(id).child("medicinetype").setValue(mtype);
                    myRef.child(id).child("qty").setValue(qty);
                    myRef.child(id).child("expdate").setValue(expdate);
                    myRef.child(id).child("mfgdate").setValue(mfgdate);
                    myRef.child(id).child("phnum").setValue(phnum);
                    myRef.child(id).child("description").setValue(description);
                    Toast.makeText(getApplicationContext(), "Product Updated Success", Toast.LENGTH_LONG).show();
                }
            }
        });


        Log.d("value : ", id);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(), UserProductViewActivity.class);
                startActivity(send);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Medicines");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = "";
                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                    NewMedicine product = SubSnapshot.getValue(NewMedicine.class);
                    String productId = SubSnapshot.getKey();
                    productId = product.getMedicineid();
                    // Adding name and phone number of student into string that is coming from server.

                    String ShowDataString = "Id : " + productId + "\nProduct Name : " + product.getMedicinename() +
                            "\nProduct Type : " + product.getMedicinetype() + "\nQuantity : " + product.getQty() +
                            "\nMfgDate : " + product.getMfrdate() + "\nExpiryDate : " + product.getExpdate() +
                            "\nDescription : " + product.getDescription();

                    Log.d("Data : ", ShowDataString);


                    if (id.equals(productId)) {
                        mnametxt.setText(product.getMedicinename());
                        mtype=product.getMedicinetype();
                        qtytxt.setText(product.getQty());
                        mfgdatetxt.setText(product.getMfrdate());
                        expdatetxt.setText(product.getExpdate());
                        descriptiontxt.setText(product.getDescription());
                    }

                    //Apply complete string variable into TextView.
                    //ShowDataTextView.setText(ShowDataString);
                    //if (str.length() == 0)
                    //    str = str + ShowDataString;
                    //else
                    //    str = str + "," + ShowDataString;
                }
                //ShowDataTextView.setText(str);
                //array = str.split(",");
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminViewProductsActivity.this,
                //        android.R.layout.simple_list_item_1, android.R.id.text1, array);
                //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Data Access Failed" + error.getMessage());
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

            c.add(Calendar.DATE, 700);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mfgdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -1470);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        } else if (v == ExpDateBtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            c.add(Calendar.DATE, 700);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            expdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -1470);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
    }
}
