package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;
import com.satanasov.contactsapp.RecyclerViewAdapter.RecyclerViewAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private                              String              mGender;
    private                               List               mUsersContactsList;

    private                              DataBase            mDB;
    private                              Context             mContext;
    private                              RecyclerView        mRecyclerView;
    private                              RecyclerViewAdapter mRecyclerAdapter;
    private                              AlertDialog.Builder mDialogBuilder;
    private                              AlertDialog         mDialog;

    private                              EditText            mFirstNameEditText;
    private                              EditText            mLastNameEditText;
    private                              EditText            mEmailNameEditText;
    private                              EditText            mPhoneNumberEditText;

    private                              Spinner             mCountrySpinner;

    private                              RadioButton         mMaleRadioButton;
    private                              RadioButton         mFemaleRadioButton;


    private                              Button              mSaveButton;
    private                              Button              mCancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        mDB = new DataBase();
        mContext = this;
        FloatingActionButton fab = findViewById(R.id.floatingButtonContactsID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();


            }
        });
        createRecyclerView();
    }

    public Comparator<User> compareByName = new Comparator<User>() {
        @Override
        public int compare(User user, User t1) {
            return user.getFirstName().compareToIgnoreCase(t1.getFirstName());
        }
    };



    public void showPopUp() {
        mDialogBuilder              = new AlertDialog.Builder(this);
        View view                   = getLayoutInflater().inflate(R.layout.user_details_popup, null);
        mFirstNameEditText          = view.findViewById(R.id.firstNameEditTextID);
        mLastNameEditText           = view.findViewById(R.id.lastNameEditTextID);
        mEmailNameEditText          = view.findViewById(R.id.emailEditTextID);
        mPhoneNumberEditText        = view.findViewById(R.id.phoneEditTextID);
        mCountrySpinner             = view.findViewById(R.id.countrySpinnerID);
        mCancelButton               = view.findViewById(R.id.cancelButtonPopUpId);
        mSaveButton                 = view.findViewById(R.id.saveButtonID);
        mMaleRadioButton            = view.findViewById(R.id.maleRadioButtonID);
        mFemaleRadioButton          = view.findViewById(R.id.femaleRadioButtonID);
        //Spinner
        countrySpinner(view);
        radioButtons();
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFirstNameEditText.getText().toString().isEmpty() && mLastNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Enter First Name and/or Last Name", Toast.LENGTH_SHORT).show();
                }
                else if(mPhoneNumberEditText.length()<10&&mPhoneNumberEditText.length()>13){
                    Toast.makeText(mContext, "Enter a valid number", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidEmail(mEmailNameEditText.getText().toString())){
                    Toast.makeText(mContext, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if(!mMaleRadioButton.isChecked()&&!mFemaleRadioButton.isChecked()){
                    Toast.makeText(mContext, "Select gender", Toast.LENGTH_SHORT).show();
                }
                else
                    saveContactToDB();
                    createRecyclerView();


            }
        });

    }
    public void saveContactToDB() {
        User user = new User(mFirstNameEditText.getText().toString(),mLastNameEditText.getText().toString(),mEmailNameEditText.getText().toString(),
                mPhoneNumberEditText.getText().toString(),mCountrySpinner.getSelectedItem().toString(),mGender);

        mDB.insertIntoDatabase(user.toString(), mContext);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();

            }
        }, 1200); //  1 second.
    }
    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public void radioButtons(){

        mMaleRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGender         = mMaleRadioButton.getText().toString();
                    mFemaleRadioButton.setChecked(false);

                }
            });
        mFemaleRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGender         = mFemaleRadioButton.getText().toString();
                    mMaleRadioButton.setChecked(false);
                }
            });

    }
    public void countrySpinner(View view) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrySpinner.setAdapter(adapter);
        mDialogBuilder.setView(view);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String countrySelected = adapterView.getItemAtPosition(i).toString();

                switch (countrySelected) {
                    case "Bulgaria":
                        mPhoneNumberEditText.setText("+359");
                        break;
                    case "Germany":
                        mPhoneNumberEditText.setText("+49");
                        break;
                    case "Netherlands":
                        mPhoneNumberEditText.setText("+31");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void createRecyclerView(){
        mRecyclerView = findViewById(R.id.contactListRecycleID);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUsersContactsList = mDB.readFromFile(mContext);
        Collections.sort(mUsersContactsList, compareByName);
        mRecyclerAdapter = new RecyclerViewAdapter(this, mUsersContactsList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();

    }



}