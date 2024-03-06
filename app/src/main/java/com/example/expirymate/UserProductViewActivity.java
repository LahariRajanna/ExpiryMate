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

public class UserProductViewActivity extends AppCompatActivity {

    private Button viewbtn, backbtn;
    private String[] array;
    private ListView listView, listView1, listView2, listView3, listView4, listView5;
    private ArrayList<String> pidarray, pnamearray, ptypearray, mfgdate, expdate;
    private ArrayList<String> pidarray1, pnamearray1, ptypearray1, mfgdate1, expdate1;
    private ArrayList<String> pidarray2, pnamearray2, ptypearray2, mfgdate2, expdate2;
    private ArrayList<String> pidarray3, pnamearray3, ptypearray3, mfgdate3, expdate3;
    private ArrayList<String> pidarray4, pnamearray4, ptypearray4, mfgdate4, expdate4;
    private String userid, mtype;
    private Spinner spinner;
    private String[] products = {"EyeBrow", "LipStick", "Powder", "Cream", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_view);

        viewbtn = (Button) findViewById(R.id.show_button);
        /*
        listView1 = (ListView) findViewById(R.id.list_view1);
        listView2 = (ListView) findViewById(R.id.list_view2);
        listView3 = (ListView) findViewById(R.id.list_view3);
        listView4 = (ListView) findViewById(R.id.list_view4);
        listView5 = (ListView) findViewById(R.id.list_view5);
        */
        backbtn = (Button) findViewById(R.id.gobackbutton);
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.list_view);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, products);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        mtype="EyeBrow";
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

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(send);
            }
        });

        /*listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Id ", pidarray.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = "";
                pidarray = new ArrayList();
                pnamearray = new ArrayList();
                ptypearray = new ArrayList();
                mfgdate = new ArrayList();
                expdate = new ArrayList();

                pidarray1 = new ArrayList();
                pnamearray1 = new ArrayList();
                ptypearray1 = new ArrayList();
                mfgdate1 = new ArrayList();
                expdate1 = new ArrayList();

                pidarray2 = new ArrayList();
                pnamearray2 = new ArrayList();
                ptypearray2 = new ArrayList();
                mfgdate2 = new ArrayList();
                expdate2 = new ArrayList();

                pidarray3 = new ArrayList();
                pnamearray3 = new ArrayList();
                ptypearray3 = new ArrayList();
                mfgdate3 = new ArrayList();
                expdate3 = new ArrayList();

                pidarray4 = new ArrayList();
                pnamearray4 = new ArrayList();
                ptypearray4 = new ArrayList();
                mfgdate4 = new ArrayList();
                expdate4 = new ArrayList();

                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                    NewProduct product = SubSnapshot.getValue(NewProduct.class);
                    String productId = SubSnapshot.getKey();
                    productId = product.getProductid();
                    // Adding name and phone number of student into string that is coming from server.

                    String ShowDataString = "Id : " + productId + "\nProduct Name : " + product.getProductname() +
                            "\nProduct Type : " + product.getProducttype() + "\nQuantity : " + product.getQty() +
                            "\nMfgDate : " + product.getMfgdate() + "\nExpDate : " + product.getExpdate() +
                            "\nDescription : " + product.getDescription();

                    if(mtype.equals(product.getProducttype())) {
                        pidarray.add(productId);
                        pnamearray.add(product.getProductname());
                        ptypearray.add(product.getProducttype());
                        mfgdate.add(product.getMfgdate());
                        expdate.add(product.getExpdate());
                    }

                    /*if(product.getProducttype().equals("EyeBrow")) {
                        pidarray.add(productId);
                        pnamearray.add(product.getProductname());
                        ptypearray.add(product.getProducttype());
                        mfgdate.add(product.getMfgdate());
                        expdate.add(product.getExpdate());
                    }
                    else if(product.getProducttype().equals("LipStick")) {
                        pidarray1.add(productId);
                        pnamearray1.add(product.getProductname());
                        ptypearray1.add(product.getProducttype());
                        mfgdate1.add(product.getMfgdate());
                        expdate1.add(product.getExpdate());
                    }
                    else if(product.getProducttype().equals("Powder")) {
                        pidarray2.add(productId);
                        pnamearray2.add(product.getProductname());
                        ptypearray2.add(product.getProducttype());
                        mfgdate2.add(product.getMfgdate());
                        expdate2.add(product.getExpdate());
                    }
                    else if(product.getProducttype().equals("Cream")) {
                        pidarray3.add(productId);
                        pnamearray3.add(product.getProductname());
                        ptypearray3.add(product.getProducttype());
                        mfgdate3.add(product.getMfgdate());
                        expdate3.add(product.getExpdate());
                    }
                    else if(product.getProducttype().equals("Others")) {
                        pidarray4.add(productId);
                        pnamearray4.add(product.getProductname());
                        ptypearray4.add(product.getProducttype());
                        mfgdate4.add(product.getMfgdate());
                        expdate4.add(product.getExpdate());
                    }*/
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


                String[] pnames, ptypes, mfgdates, expdates;
                pnames = new String[pnamearray.size()];
                ptypes = new String[pnamearray.size()];
                mfgdates = new String[pnamearray.size()];
                expdates = new String[pnamearray.size()];
                for (int i = 0; i < pnamearray.size(); i++) {
                    pnames[i] = pnamearray.get(i);
                    ptypes[i] = ptypearray.get(i);
                    mfgdates[i] = mfgdate.get(i);
                    expdates[i] = expdate.get(i);
                }
                Context context = getApplicationContext();
                MyListAdapter adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(adapter);

                /*
                pnames = new String[pnamearray1.size()];
                ptypes = new String[pnamearray1.size()];
                mfgdates = new String[pnamearray1.size()];
                expdates = new String[pnamearray1.size()];
                for (int i = 0; i < pnamearray1.size(); i++) {
                    pnames[i] = pnamearray1.get(i);
                    ptypes[i] = ptypearray1.get(i);
                    mfgdates[i] = mfgdate1.get(i);
                    expdates[i] = expdate1.get(i);
                }
                context = getApplicationContext();
                adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView2 = (ListView) findViewById(R.id.list_view2);
                listView2.setAdapter(adapter);

                pnames = new String[pnamearray2.size()];
                ptypes = new String[pnamearray2.size()];
                mfgdates = new String[pnamearray2.size()];
                expdates = new String[pnamearray2.size()];
                for (int i = 0; i < pnamearray2.size(); i++) {
                    pnames[i] = pnamearray2.get(i);
                    ptypes[i] = ptypearray2.get(i);
                    mfgdates[i] = mfgdate2.get(i);
                    expdates[i] = expdate2.get(i);
                }
                context = getApplicationContext();
                adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView3 = (ListView) findViewById(R.id.list_view3);
                listView3.setAdapter(adapter);


                pnames = new String[pnamearray3.size()];
                ptypes = new String[pnamearray3.size()];
                mfgdates = new String[pnamearray3.size()];
                expdates = new String[pnamearray3.size()];
                for (int i = 0; i < pnamearray3.size(); i++) {
                    pnames[i] = pnamearray3.get(i);
                    ptypes[i] = ptypearray3.get(i);
                    mfgdates[i] = mfgdate3.get(i);
                    expdates[i] = expdate3.get(i);
                }
                context = getApplicationContext();
                adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView4 = (ListView) findViewById(R.id.list_view4);
                listView4.setAdapter(adapter);


                pnames = new String[pnamearray4.size()];
                ptypes = new String[pnamearray4.size()];
                mfgdates = new String[pnamearray4.size()];
                expdates = new String[pnamearray4.size()];
                for (int i = 0; i < pnamearray4.size(); i++) {
                    pnames[i] = pnamearray4.get(i);
                    ptypes[i] = ptypearray4.get(i);
                    mfgdates[i] = mfgdate4.get(i);
                    expdates[i] = expdate4.get(i);
                }
                context = getApplicationContext();
                adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                listView5 = (ListView) findViewById(R.id.list_view5);
                listView5.setAdapter(adapter);*/
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
                expdate = new ArrayList();

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
                            // Adding name and phone number of student into string that is coming from server.

                            String ShowDataString = "Id : " + productId + "\nProduct Name : " + product.getProductname() +
                                    "\nProduct Type : " + product.getProducttype() + "\nQuantity : " + product.getQty() +
                                    "\nMfgDate : " + product.getMfgdate() + "\nExpDate : " + product.getExpdate() +
                                    "\nDescription : " + product.getDescription();

                            if(mtype.equals(product.getProducttype())) {
                                pidarray.add(productId);
                                pnamearray.add(product.getProductname());
                                ptypearray.add(product.getProducttype());
                                mfgdate.add(product.getMfgdate());
                                expdate.add(product.getExpdate());
                            }

                            /*
                            if(product.getProducttype().equals("EyeBrow")) {
                                pidarray.add(productId);
                                pnamearray.add(product.getProductname());
                                ptypearray.add(product.getProducttype());
                                mfgdate.add(product.getMfgdate());
                                expdate.add(product.getExpdate());
                            }
                            else if(product.getProducttype().equals("LipStick")) {
                                pidarray1.add(productId);
                                pnamearray1.add(product.getProductname());
                                ptypearray1.add(product.getProducttype());
                                mfgdate1.add(product.getMfgdate());
                                expdate1.add(product.getExpdate());
                            }
                            else if(product.getProducttype().equals("Powder")) {
                                pidarray2.add(productId);
                                pnamearray2.add(product.getProductname());
                                ptypearray2.add(product.getProducttype());
                                mfgdate2.add(product.getMfgdate());
                                expdate2.add(product.getExpdate());
                            }
                            else if(product.getProducttype().equals("Cream")) {
                                pidarray3.add(productId);
                                pnamearray3.add(product.getProductname());
                                ptypearray3.add(product.getProducttype());
                                mfgdate3.add(product.getMfgdate());
                                expdate3.add(product.getExpdate());
                            }
                            else if(product.getProducttype().equals("Others")) {
                                pidarray4.add(productId);
                                pnamearray4.add(product.getProductname());
                                ptypearray4.add(product.getProducttype());
                                mfgdate4.add(product.getMfgdate());
                                expdate4.add(product.getExpdate());
                            }
                            */
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


                        String[] pnames, ptypes, mfgdates, expdates;
                        pnames = new String[pnamearray.size()];
                        ptypes = new String[pnamearray.size()];
                        mfgdates = new String[pnamearray.size()];
                        expdates = new String[pnamearray.size()];
                        for (int i = 0; i < pnamearray.size(); i++) {
                            pnames[i] = pnamearray.get(i);
                            ptypes[i] = ptypearray.get(i);
                            mfgdates[i] = mfgdate.get(i);
                            expdates[i] = expdate.get(i);
                        }
                        if(pnamearray.isEmpty())
                        {
                            Log.d("Empty : ", "No data");
                            Toast.makeText(UserProductViewActivity.this, "Data Not Found", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Context context = getApplicationContext();
                            MyListAdapter adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                            listView = (ListView) findViewById(R.id.list_view);
                            listView.setAdapter(adapter);
                        }
                        /*pnames = new String[pnamearray1.size()];
                        ptypes = new String[pnamearray1.size()];
                        mfgdates = new String[pnamearray1.size()];
                        expdates = new String[pnamearray1.size()];
                        for (int i = 0; i < pnamearray1.size(); i++) {
                            pnames[i] = pnamearray1.get(i);
                            ptypes[i] = ptypearray1.get(i);
                            mfgdates[i] = mfgdate1.get(i);
                            expdates[i] = expdate1.get(i);
                        }
                        context = getApplicationContext();
                        adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                        listView2 = (ListView) findViewById(R.id.list_view);
                        listView2.setAdapter(adapter);

                        pnames = new String[pnamearray2.size()];
                        ptypes = new String[pnamearray2.size()];
                        mfgdates = new String[pnamearray2.size()];
                        expdates = new String[pnamearray2.size()];
                        for (int i = 0; i < pnamearray2.size(); i++) {
                            pnames[i] = pnamearray2.get(i);
                            ptypes[i] = ptypearray2.get(i);
                            mfgdates[i] = mfgdate2.get(i);
                            expdates[i] = expdate2.get(i);
                        }
                        context = getApplicationContext();
                        adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                        listView3 = (ListView) findViewById(R.id.list_view);
                        listView3.setAdapter(adapter);


                        pnames = new String[pnamearray3.size()];
                        ptypes = new String[pnamearray3.size()];
                        mfgdates = new String[pnamearray3.size()];
                        expdates = new String[pnamearray3.size()];
                        for (int i = 0; i < pnamearray3.size(); i++) {
                            pnames[i] = pnamearray3.get(i);
                            ptypes[i] = ptypearray3.get(i);
                            mfgdates[i] = mfgdate3.get(i);
                            expdates[i] = expdate3.get(i);
                        }
                        context = getApplicationContext();
                        adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                        listView4 = (ListView) findViewById(R.id.list_view);
                        listView4.setAdapter(adapter);


                        pnames = new String[pnamearray4.size()];
                        ptypes = new String[pnamearray4.size()];
                        mfgdates = new String[pnamearray4.size()];
                        expdates = new String[pnamearray4.size()];
                        for (int i = 0; i < pnamearray4.size(); i++) {
                            pnames[i] = pnamearray4.get(i);
                            ptypes[i] = ptypearray4.get(i);
                            mfgdates[i] = mfgdate4.get(i);
                            expdates[i] = expdate4.get(i);
                        }
                        context = getApplicationContext();
                        adapter = new MyListAdapter(UserProductViewActivity.this, pnames, ptypes, mfgdates, expdates);
                        listView5 = (ListView) findViewById(R.id.list_view);
                        listView5.setAdapter(adapter);
                        */
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
                Intent intent = new Intent(getApplicationContext(), UserViewProductActivity.class);
                intent.putExtra("id", pidarray.get(position));
                startActivity(intent);
            }
        });
    }
}