package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.PopUpDialog;
import com.satanasov.contactsapp.R;
import com.satanasov.contactsapp.RecyclerViewAdapter.RecyclerViewAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements PopUpDialog.popUpListener {
    private                              String              mGender;
    private                              List               mUsersContactsList;

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

    private                          FloatingActionButton            fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        mDB = new DataBase();
        mContext = this;
        fab = findViewById(R.id.floating_button_contact_list_id);
        createRecyclerView();

    }
    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpDialog popUp = new PopUpDialog();
                popUp.show(getSupportFragmentManager(),"PopUpActivity");


            }
        });

    }

    public Comparator<User> compareByName = new Comparator<User>() {
        @Override
        public int compare(User user, User t1) {
            return user.getFirstName().compareToIgnoreCase(t1.getFirstName());
        }
    };



//    public void showPopUp() {
//        mDialogBuilder              = new AlertDialog.Builder(this);
//        View view                   = getLayoutInflater().inflate(R.layout.user_details_popup, null);
//        mFirstNameEditText          = view.findViewById(R.id.first_name_user_details_id);
//        mLastNameEditText           = view.findViewById(R.id.last_name_user_details_id);
//        mEmailNameEditText          = view.findViewById(R.id.email_user_details_id);
//        mPhoneNumberEditText        = view.findViewById(R.id.phone_number_user_details_id);
//        mCountrySpinner             = view.findViewById(R.id.country_spiner_user_details_id);
//        mCancelButton               = view.findViewById(R.id.cancelBtn_user_details_id);
//        mSaveButton                 = view.findViewById(R.id.saveBtn_user_details_id);
//        mMaleRadioButton            = view.findViewById(R.id.male_radioBtn_user_details_id);
//        mFemaleRadioButton          = view.findViewById(R.id.female_user_details_id);
//        //Spinner
//        countrySpinner(view);
//        radioButtons();
//        mCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
//        mSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mFirstNameEditText.getText().toString().isEmpty() && mLastNameEditText.getText().toString().isEmpty()) {
//                    Toast.makeText(mContext, "Enter First Name and/or Last Name", Toast.LENGTH_SHORT).show();
//                }
//                else if(mPhoneNumberEditText.length()<10&&mPhoneNumberEditText.length()>13){
//                    Toast.makeText(mContext, "Enter a valid number", Toast.LENGTH_SHORT).show();
//                }
//                else if(!isValidEmail(mEmailNameEditText.getText().toString())){
//                    Toast.makeText(mContext, "Enter a valid email address", Toast.LENGTH_SHORT).show();
//                }
//                else if(!mMaleRadioButton.isChecked()&&!mFemaleRadioButton.isChecked()){
//                    Toast.makeText(mContext, "Select gender", Toast.LENGTH_SHORT).show();
//                }
//                else
//                    saveContactToDB();
//                    createRecyclerView();
//
//
//            }
//        });
//
//    }
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
    public void createRecyclerView(){
        mRecyclerView = findViewById(R.id.contact_list_recycler_id);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUsersContactsList = mDB.readFromFile(mContext);
        Collections.sort(mUsersContactsList, compareByName);
        mRecyclerAdapter = new RecyclerViewAdapter(this, mUsersContactsList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onSave(DialogFragment dialog) {

    }

    @Override
    public void onCancel(DialogFragment dialog) {

    }
}