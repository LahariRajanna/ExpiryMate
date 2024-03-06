package com.example.expirymate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewUserActivity extends AppCompatActivity {

    private EditText fnametxt, lnametxt, unametxt, pwdtxt, emailidtxt, phnumtxt, addresstxt;
    private String customerid, fname, lname, uname, pwd, emailid, phnum, address, gender;
    private Button newbtn, gobackbtn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Boolean flag = false;

    //Accept Only Alphabet in EditText
    public static InputFilter acceptonlyAlphabetValuesnotNumbersMethod() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean isCheck = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) {
                        sb.append(c);
                    } else {
                        isCheck = false;
                    }
                }
                if (isCheck)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString spannableString = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, spannableString, 0);
                        return spannableString;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
                Matcher match = pattern.matcher(String.valueOf(c));
                return match.matches();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        fnametxt = (EditText) findViewById(R.id.editTextfirstname);
        lnametxt = (EditText) findViewById(R.id.editTextlastname);
        unametxt = (EditText) findViewById(R.id.editTextusername);
        pwdtxt = (EditText) findViewById(R.id.editTextpassword);
        emailidtxt = (EditText) findViewById(R.id.editEmailid);
        phnumtxt = (EditText) findViewById(R.id.editTextphnum);
        addresstxt = (EditText) findViewById(R.id.editAddress);
        gobackbtn = (Button) findViewById(R.id.gobackbutton);
        newbtn = (Button) findViewById(R.id.newuserbutton);
        radioGroup = (RadioGroup) findViewById(R.id.genderradioGroup);

        fnametxt.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});
        lnametxt.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});
        //fnametxt.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        //lnametxt.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                gender = radioButton.getText().toString();
            }
        });

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                startActivity(intent);
            }
        });

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //customerid = customeridtxt.getText().toString();
                fname = fnametxt.getText().toString();
                lname = lnametxt.getText().toString();
                uname = unametxt.getText().toString();
                pwd = pwdtxt.getText().toString();
                emailid = emailidtxt.getText().toString();
                phnum = phnumtxt.getText().toString();
                address = addresstxt.getText().toString();

                Pattern pattern = Pattern.compile("^\\d{10}$");
                Matcher matcher = pattern.matcher(phnum);

                Pattern pwdpattern = Pattern.compile("/^[a-z0-9]{8}$/");
                Matcher pwdmatcher = pwdpattern.matcher(pwd);


                if (TextUtils.isEmpty(fname)) {
                    fnametxt.setError("FirstName is Empty");
                    fnametxt.setFocusable(true);
                } else if (TextUtils.isEmpty(lname)) {
                    lnametxt.setError("LastName is Empty");
                    lnametxt.setFocusable(true);
                } else if (TextUtils.isEmpty(uname)) {
                    unametxt.setError("UserName is Empty");
                    unametxt.setFocusable(true);
                } else if (TextUtils.isEmpty(pwd)) {
                    pwdtxt.setError("Password is Empty");
                    pwdtxt.setFocusable(true);
                }
                /*else if(!pwdmatcher.matches())
                {
                    pwdtxt.setError("Password should be 8 characters");
                    pwdtxt.setFocusable(true);
                }*/
                //pwd validation is done
                else if (TextUtils.isEmpty(phnum)) {
                    phnumtxt.setError("PhoneNumber is Empty");
                    phnumtxt.setFocusable(true);
                } else if (!matcher.matches()) {
                    phnumtxt.setError("PhoneNumber is Not Valid");
                    phnumtxt.setFocusable(true);
                }//phone number validation is done
                else if (TextUtils.isEmpty(emailid)) {
                    emailidtxt.setError("EmailId is Empty");
                    emailidtxt.setFocusable(true);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                    emailidtxt.setError("EmailId is Not Valid");
                    emailidtxt.setFocusable(true);
                }//Email validation is done
                else if (TextUtils.isEmpty(address)) {
                    addresstxt.setError("Address is Empty");
                    addresstxt.setFocusable(true);
                } else {
                    Log.d("UserName  : ", uname);
                    Log.d("Email Id  : ", emailid);
                    Log.d("Phone Num : ", phnum);
                    Log.d("------------", "-------");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Customer");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot SubSnapshot : snapshot.getChildren()) {
                                NewUser newCustomer = SubSnapshot.getValue(NewUser.class);
                                Log.d("------------", "-------");
                                System.out.println("UserName : " + uname  + " " + newCustomer.getUsername() + " " +
                                        uname.equals(newCustomer.getUsername()));
                                System.out.println("UserName : " + emailid  + " " + newCustomer.getEmailid()+ " "+
                                        emailid.equals(newCustomer.getEmailid()));
                                System.out.println("UserName : " + phnum  + " " + newCustomer.getPhonenum()+ " "+
                                        phnum.equals(newCustomer.getPhonenum()));
                                if (uname.equals(newCustomer.getUsername()) || emailid.equals(newCustomer.getEmailid())
                                        || phnum.equals(newCustomer.getPhonenum())) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if (!flag) {
                        DatabaseReference dbRef = database.getReference();
                        dbRef = database.getReference("Customer");
                        customerid = dbRef.push().getKey();
                        NewUser newCustomer = new NewUser(customerid, fname, lname, uname, pwd, emailid, phnum,
                                gender, address);
                        //dbRef.child("Student").child(StudentId).setValue(student);
                        //dbRef.child(StudentId).setValue(student);
                        dbRef.child(customerid).setValue(newCustomer);

                        Toast.makeText(NewUserActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();

                        //customeridtxt.setText("");
                        fnametxt.setText("");
                        lnametxt.setText("");
                        unametxt.setText("");
                        pwdtxt.setText("");
                        addresstxt.setText("");
                        phnumtxt.setText("");
                        emailidtxt.setText("");
                    }
                    else
                    {
                        Toast.makeText(NewUserActivity.this, "Duplicate UserName/PhoneNum/EmailId exists", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}