package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.satanasov.contactsapp.R;

public class DetailsActivity extends AppCompatActivity {
    private                  TextView        mFullName;
    private                  TextView        mPhoneNumber;
    private                  TextView        mEmail;
    private                  TextView        mCountry;
    private                  TextView        mGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mFullName               = findViewById(R.id.name_details_id);
        mPhoneNumber            = findViewById(R.id.phone_details_id);
        mEmail                  = findViewById(R.id.email_details_id);
        mCountry                = findViewById(R.id.country_details_id);
        mGender                 = findViewById(R.id.gender_details_id);

        Bundle bundle = getIntent().getExtras();

        if(bundle !=null){
            mFullName.setText(bundle.getString("fullName"));
            mPhoneNumber.setText(bundle.getString("phoneNumber"));
            mEmail.setText(bundle.getString("email"));
            mCountry.setText(bundle.getString("country"));
            mGender.setText(bundle.getString("gender"));

        }
    }
}