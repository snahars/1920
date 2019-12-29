package com.notes.iit.simplenotesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class UserProfileActivity extends AppCompatActivity {

    TextView emailView;
    TextView nameView;
    TextView genderView;
    TextView contactView;

    ImageView editProfile;

    String emailVal;
    String gender;
    String firstName;
    String lastName, contactNo;

    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sqliteHelper = new SqliteHelper(this);

        initializeFields();

        final String email = getIntent().getExtras().getString("email");

        getAndSetValue(email);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UserProfileActivity.this, RegisterActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });
    }

    private void initializeFields() {
        emailView = (TextView) findViewById(R.id.emailView);
        nameView = (TextView) findViewById(R.id.nameView);
        genderView = (TextView) findViewById(R.id.genderView);
        contactView = (TextView) findViewById(R.id.contactView);
        editProfile = (ImageView) findViewById(R.id.editProfile);
    }

    private void getAndSetValue(String email) {
        User user = sqliteHelper.retreiveUserByEmail(email);
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
        contactNo = user.getContactNo();

        nameView.setText(firstName + " " + lastName);
        emailView.setText(email);
        genderView.setText(gender);
        contactView.setText(contactNo);
    }

}
