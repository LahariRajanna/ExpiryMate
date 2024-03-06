package com.example.expirymate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMedicineViewActivity extends AppCompatActivity {

    private Button viewbtn, backbtn;
    private String[] array;
    private ListView listView;
    private ArrayList<String> pidarray, pnamearray, ptypearray, mfgdate, expdate;
    private Spinner spinner;
    private String[] products = {"Syrups", "Tablets", "Powder", "Ointments", "Others"};
    private String mtype, userid, phnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medicine_view);

        viewbtn = (Button) findViewById(R.id.show_button);
        listView = (ListView) findViewById(R.id.list_view);
        backbtn = (Button) findViewById(R.id.gobackbutton);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        phnum = intent.getStringExtra("phnum");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(send);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        mtype="Syrups";
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, products);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype = products[position];
                Log.d("Medicine Type : ",mtype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Medicines");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = "";
                pidarray = new ArrayList();
                pnamearray = new ArrayList();
                ptypearray = new ArrayList();
                mfgdate = new ArrayList();
                expdate= new ArrayList();
                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                    NewMedicine product = SubSnapshot.getValue(NewMedicine.class);
                    String productId = SubSnapshot.getKey();
                    productId = product.getMedicineid();
                    // Adding name and phone number of student into string that is coming from server.

                    String ShowDataString = "Id : " + productId + "\nMedicine Name : " + product.getMedicinename() +
                            "\nMedicine Type : " + product.getMedicinetype() + "\nQuantity : " + product.getQty() +
                            "\nMfgDate : " + product.getMfrdate() + "\nExpiry Date : " + product.getExpdate() +
                            "\nDescription : " + product.getDescription();

                    if(mtype.equals(product.getMedicinetype())) {
                        pidarray.add(productId);
                        pnamearray.add(product.getMedicinename());
                        ptypearray.add(product.getMedicinetype());
                        mfgdate.add(product.getMfrdate());
                        expdate.add(product.getExpdate());
                    }
                }

                String[] pnames, ptypes, mfgdates, expdates;
                pnames = new String[pnamearray.size()];
                ptypes = new String[pnamearray.size()];
                mfgdates= new String[pnamearray.size()];
                expdates= new String[pnamearray.size()];
                for (int i = 0; i < pnamearray.size(); i++) {
                    pnames[i] = pnamearray.get(i);
                    ptypes[i] = ptypearray.get(i);
                    mfgdates[i] = mfgdate.get(i);
                    expdates[i] = expdate.get(i);
                }
                Context context = getApplicationContext();
                MyListAdapter adapter = new MyListAdapter(UserMedicineViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Data Access Failed" + error.getMessage());
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adding addValueEventListener method on firebase object.
                pidarray = new ArrayList();
                pnamearray = new ArrayList();
                ptypearray = new ArrayList();
                mfgdate = new ArrayList();
                expdate= new ArrayList();
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

                            String ShowDataString = "Id : " + productId + "\nMedicine Name : " + product.getMedicinename() +
                                    "\nMedicine Type : " + product.getMedicinetype() + "\nQuantity : " + product.getQty() +
                                    "\nMfgDate : " + product.getMfrdate() + "\nExpiry Date : " + product.getExpdate() +
                                    "\nDescription : " + product.getDescription();
                            if(mtype.equals(product.getMedicinetype()) && userid.equals(product.getUserid())) {
                                pidarray.add(productId);
                                pnamearray.add(product.getMedicinename());
                                ptypearray.add(product.getMedicinetype());
                                mfgdate.add(product.getMfrdate());
                                expdate.add(product.getExpdate());
                            }
                        }

                        String[] pnames, ptypes, mfgdates, expdates;
                        pnames = new String[pnamearray.size()];
                        ptypes = new String[pnamearray.size()];
                        mfgdates= new String[pnamearray.size()];
                        expdates= new String[pnamearray.size()];
                        for (int i = 0; i < pnamearray.size(); i++) {
                            pnames[i] = pnamearray.get(i);
                            ptypes[i] = ptypearray.get(i);
                            mfgdates[i] = mfgdate.get(i);
                            expdates[i] = expdate.get(i);
                        }
                        Log.d("Array ", "Size : " + pnamearray.size());
                        if(pnamearray.isEmpty())
                        {
                            Log.d("Empty : ", "No data");
                            Toast.makeText(UserMedicineViewActivity.this, "Data Not Found", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Context context = getApplicationContext();
                            MyListAdapter adapter = new MyListAdapter(UserMedicineViewActivity.this, pnames, ptypes, mfgdates, expdates);
                            listView = (ListView) findViewById(R.id.list_view);
                            listView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Data Access Failed" + error.getMessage());
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click Id ", pidarray.get(position));
                Intent intent = new Intent(getApplicationContext(), UserViewMedicineActivity.class);
                intent.putExtra("id", pidarray.get(position));
                startActivity(intent);
            }
        });
    }
}