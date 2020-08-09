package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailNameEditText;
    private EditText phoneNumberEditText;
    private Spinner countrySpinner;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private String gender;
    private Button saveButton;
    private Button cancelButton;
    private DataBase db;
    private Context context;
    private List userContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = new DataBase();
        context = this;
        userContacts = db.readFromFile(context);
        isContactsEmpty();

        FloatingActionButton fab = findViewById(R.id.floatingButtonMainID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();

            }
        });

    }

    public void showPopUp() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.user_details_popup, null);
        firstNameEditText = view.findViewById(R.id.firstNameEditTextID);
        lastNameEditText = view.findViewById(R.id.lastNameEditTextID);
        emailNameEditText = view.findViewById(R.id.emailEditTextID);
        phoneNumberEditText = view.findViewById(R.id.phoneEditTextID);
        countrySpinner = view.findViewById(R.id.countrySpinnerID);
        cancelButton = view.findViewById(R.id.cancelButtonPopUpId);
        saveButton = view.findViewById(R.id.saveButtonID);
        maleRadioButton = view.findViewById(R.id.maleRadioButtonID);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButtonID);
        //Spinner
        countrySpinner(view);
        radioButtons();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstNameEditText.getText().toString().isEmpty() && !lastNameEditText.getText().toString().isEmpty()
                        && !emailNameEditText.getText().toString().isEmpty() && !phoneNumberEditText.getText().toString().isEmpty()) {
                    saveContactToDB();

                }
            }
        });

    }
    public void radioButtons(){
        maleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender=maleRadioButton.getText().toString();
                femaleRadioButton.setChecked(false);

            }
        });
        femaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender=femaleRadioButton.getText().toString();
                maleRadioButton.setChecked(false);
            }
        });

    }

    public void countrySpinner(View view) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String countrySelected = adapterView.getItemAtPosition(i).toString();

                switch (countrySelected) {
                    case "Bulgaria":
                        phoneNumberEditText.setText("+359");
                        break;
                    case "Germany":
                        phoneNumberEditText.setText("+49");
                        break;
                    case "Netherlands":
                        phoneNumberEditText.setText("+31");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void saveContactToDB() {
        User user = new User();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String country = countrySpinner.getSelectedItem().toString();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.setCountry(country);

        db.insertIntoDatabase(user.toString(), context);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ContactListActivity.class));
                finish();
            }
        }, 1200); //  1 second.
    }

    public void isContactsEmpty() {
        if (!userContacts.isEmpty()) {
            startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            finish();
        }

    }


}