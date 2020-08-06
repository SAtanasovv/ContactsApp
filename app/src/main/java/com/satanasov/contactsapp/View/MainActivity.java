package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;

import java.util.ArrayList;
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
    private RadioGroup genderGroup;
    private Button saveButton;
    private DataBase db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db= new DataBase();
        context=this;
        FloatingActionButton fab = findViewById(R.id.floatingButtonMainID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });

    }
    public void showPopUp(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.user_details_popup,null);
        firstNameEditText=view.findViewById(R.id.firstNameEditTextID);
        lastNameEditText=view.findViewById(R.id.lastNameEditTextID);
        emailNameEditText=view.findViewById(R.id.emailEditTextID);
        phoneNumberEditText=view.findViewById(R.id.phoneEditTextID);
        //countrySpinner=findViewById(R.id.countrySpinnerID);
       // genderGroup=findViewById(R.id.genderGroupID);
        saveButton= view.findViewById(R.id.saveButtonID);

//        maleRadioButton=findViewById(R.id.maleRadioButtonID);
//        femaleRadioButton=findViewById(R.id.femaleRadioButtonID);


        dialogBuilder.setView(view);
        dialog=dialogBuilder.create();
        dialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstNameEditText.getText().toString().isEmpty()&&!lastNameEditText.getText().toString().isEmpty()
                        &&!emailNameEditText.getText().toString().isEmpty()&&!phoneNumberEditText.getText().toString().isEmpty()){
           saveContactToDB();
                }
            }
        });

    }
    public void saveContactToDB(){
        User user = new User();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        db.insertIntoDatabase(user.toString(),context);
        System.out.println(db.readFromFile(context));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            }
        }, 1200); //  1 second.
    }

}