package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.satanasov.contactsapp.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView fullName;
    private TextView phoneNumber;
    private TextView email;
    private TextView country;
    private TextView gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fullName = findViewById(R.id.nameDetailsID);
        phoneNumber = findViewById(R.id.phoneDetailsId);
        email = findViewById(R.id.emailDetailsID);
        country = findViewById(R.id.countryDetailsID);
        gender = findViewById(R.id.genderDetailsID);

        Bundle bundle = getIntent().getExtras();

        if(bundle !=null){
            fullName.setText(bundle.getString("fullName"));
            phoneNumber.setText(bundle.getString("phoneNumber"));
            email.setText(bundle.getString("email"));
            country.setText(bundle.getString("country"));
            gender.setText(bundle.getString("gender"));

        }
    }
}