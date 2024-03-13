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
    private int duplicatesFound = 0;

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

        fnametxt = findViewById(R.id.editTextfirstname);
        lnametxt = findViewById(R.id.editTextlastname);
        unametxt = findViewById(R.id.editTextusername);
        pwdtxt = findViewById(R.id.editTextpassword);
        emailidtxt = findViewById(R.id.editEmailid);
        phnumtxt = findViewById(R.id.editTextphnum);
        addresstxt = findViewById(R.id.editAddress);
        gobackbtn = findViewById(R.id.gobackbutton);
        newbtn = findViewById(R.id.newuserbutton);
        radioGroup = findViewById(R.id.genderradioGroup);

        fnametxt.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});
        lnametxt.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
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
                fname = fnametxt.getText().toString();
                lname = lnametxt.getText().toString();
                uname = unametxt.getText().toString();
                pwd = pwdtxt.getText().toString();
                emailid = emailidtxt.getText().toString();
                phnum = phnumtxt.getText().toString();
                address = addresstxt.getText().toString();

                Pattern pattern = Pattern.compile("^\\d{10}$");
                Matcher matcher = pattern.matcher(phnum);

                if (TextUtils.isEmpty(fname)) {
                    fnametxt.setError("FirstName is Empty");
                    fnametxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lname)) {
                    lnametxt.setError("LastName is Empty");
                    lnametxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uname)) {
                    unametxt.setError("UserName is Empty");
                    unametxt.requestFocus();
                    return;
                }
                if (!isValidUsername(uname)) {
                    unametxt.setError("Username must be Alphanumeric Characters with special char, lenght 8-15 ");
                    unametxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    pwdtxt.setError("Password is Empty");
                    pwdtxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phnum)) {
                    phnumtxt.setError("PhoneNumber is Empty");
                    phnumtxt.requestFocus();
                    return;
                }
                if (!matcher.matches()) {
                    phnumtxt.setError("PhoneNumber is Not Valid");
                    phnumtxt.requestFocus();
                    return;
                }

                checkDuplicateAndInsert();
            }
        });
    }

    private void checkDuplicateAndInsert() {
        duplicatesFound = 0; // Reset duplicates count
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Customer");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot SubSnapshot : snapshot.getChildren()) {
                    NewUser newCustomer = SubSnapshot.getValue(NewUser.class);
                    if (newCustomer != null && (uname.equals(newCustomer.getUsername())
                            || emailid.equals(newCustomer.getEmailid())
                            || phnum.equals(newCustomer.getPhonenum()))) {
                        duplicatesFound++;
                    }
                }
                if (duplicatesFound == 0) {
                    insertUserData();
                } else {
                    Toast.makeText(NewUserActivity.this, "UserName/PhoneNum already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NewUserActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertUserData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("Customer");
        customerid = dbRef.push().getKey();
        NewUser newCustomer = new NewUser(customerid, fname, lname, uname, pwd, emailid, phnum,
                gender, address);
        dbRef.child(customerid).setValue(newCustomer);

        Toast.makeText(NewUserActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();

        fnametxt.setText("");
        lnametxt.setText("");
        unametxt.setText("");
        pwdtxt.setText("");
        addresstxt.setText("");
        phnumtxt.setText("");
        emailidtxt.setText("");
    }

    private boolean isValidUsername(String username) {
        // Regex pattern for the username validation
        String regex = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=\\S+$).{8,15}$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create matcher object
        Matcher matcher = pattern.matcher(username);
        // Return true if the username matches the pattern, otherwise false
        return matcher.matches();
    }
}
