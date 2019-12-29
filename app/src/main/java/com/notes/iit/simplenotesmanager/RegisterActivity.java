package com.notes.iit.simplenotesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextContactNo;
    Spinner dropdownGender;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;
    Button buttonRegister;

    String firstName, lastName, email, password, gender, contactNo;
    private String emailFromIntent = null;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        createGenderDropdown();

        /**for edit purpose*/
        if (getIntent().getExtras() != null) {
            emailFromIntent = getIntent().getExtras().getString("email");
            getAndSetValue(emailFromIntent);
        }
       

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    getFieldValues();

                    if (getIntent().getExtras() == null || (emailFromIntent == null)) {
                        if (!sqliteHelper.isEmailExists(email)) {

                            sqliteHelper.addUser(new User(email, password, firstName, lastName, gender, contactNo));
                            Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                            Intent intent=new Intent(RegisterActivity.this, UserProfileActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                           /* new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, Snackbar.LENGTH_LONG);*/
                        } else {

                            Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    /**for edit purpose*/
                    else {
                        sqliteHelper.updateUser(new User(email, password, firstName, lastName, gender, contactNo));
                        Intent intent=new Intent(RegisterActivity.this, UserProfileActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        Snackbar.make(buttonRegister, "User updated successfully ", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        dropdownGender = (Spinner) findViewById(R.id.gender);
        editTextContactNo = (EditText) findViewById(R.id.editTextContactNo);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

    }

    private void getFieldValues() {
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        gender = String.valueOf(dropdownGender.getSelectedItem());
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        contactNo = editTextContactNo.getText().toString();

    }

    private void createGenderDropdown() {
        String[] items = new String[]{"Select Gender" , "Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdownGender.setAdapter(adapter);
    }


    public boolean validate() {
        boolean valid = false;

        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            valid = true;
            textInputLayoutPassword.setError(null);
        }

        return valid;
    }


    private void getAndSetValue(String email) {
        User user = sqliteHelper.retreiveUserByEmail(email);
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
        contactNo = user.getContactNo();
        password = user.getPassword();

        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);
        editTextEmail.setText(email);
        editTextPassword.setText(password);
        editTextContactNo.setText(contactNo);

        editTextEmail.setFocusable(false);

        int position;
        if (gender.equals("Male")) {
            position = 1;
        }
        else if(gender.equals("Female")) {
            position = 2;
        }
        else {
            position = 3;
        }
        dropdownGender.setSelection(position);
    }

}