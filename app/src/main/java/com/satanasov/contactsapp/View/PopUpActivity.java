package com.satanasov.contactsapp.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;

public class PopUpActivity extends DialogFragment {
    private                          DataBase                        mDB;
    private                          AlertDialog.Builder             mDialogBuilder;
    private                          AlertDialog                     mDialog;
    private                          EditText                        mFirstNameEditText;
    private                          EditText                        mLastNameEditText;
    private                          EditText                        mEmailNameEditText;
    private                          EditText                        mPhoneNumberEditText;
    private                          Spinner                         mCountrySpinner;
    private                          RadioButton                     maleRadioButton;
    private                          RadioButton                     mFemaleRadioButton;
    private                          String                          mGender;
    private                          Button                          mSaveButton;
    private                          Button                          mCancelButton;
    private                          Context                         mContext;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        showPopUp();
        return mDialogBuilder.create();
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        initComponents();
//    }

    public void showPopUp() {
        mDialogBuilder          = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        mDialogBuilder.setView(inflater.inflate(R.layout.user_details_popup, null))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       if(!fieldsAreEmpty()){
                    listener.onSave(PopUpActivity.this);}
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onCancel(PopUpActivity.this);

            }
        });


    }

    public void initComponents(){

        View view               = getLayoutInflater().inflate(R.layout.user_details_popup, null);
        mFirstNameEditText      = view.findViewById(R.id.first_name_user_details_id);
        mLastNameEditText       = view.findViewById(R.id.last_name_user_details_id);
        mEmailNameEditText      = view.findViewById(R.id.email_user_details_id);
        mPhoneNumberEditText    = view.findViewById(R.id.phone_number_user_details_id);
        mCountrySpinner         = view.findViewById(R.id.country_spiner_user_details_id);
        mCancelButton           = view.findViewById(R.id.cancelBtn_user_details_id);
        mSaveButton             = view.findViewById(R.id.saveBtn_user_details_id);
        maleRadioButton         = view.findViewById(R.id.male_radioBtn_user_details_id);
        mFemaleRadioButton      = view.findViewById(R.id.female_user_details_id);
        //Spinner
        countrySpinner(view);
        radioButtons();
//        mCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
//        mSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    listener.onSave(PopUpActivity.this);
//            }
//        });

    }
    public boolean fieldsAreEmpty(){
        if (mFirstNameEditText.getText().toString().isEmpty() && mLastNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Enter First Name and/or Last Name", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(mPhoneNumberEditText.length()<10&&mPhoneNumberEditText.length()>13){
            Toast.makeText(mContext, "Enter a valid number", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(!isValidEmail(mEmailNameEditText.getText().toString())){
            Toast.makeText(mContext, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(!maleRadioButton.isChecked()&&!mFemaleRadioButton.isChecked()){
            Toast.makeText(mContext, "Select gender", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
    public void radioButtons(){
        maleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGender         =maleRadioButton.getText().toString();
                mFemaleRadioButton.setChecked(false);

            }
        });
        mFemaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGender         =mFemaleRadioButton.getText().toString();
                maleRadioButton.setChecked(false);
            }
        });

    }
    public void countrySpinner(View view) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(mContext, R.array.countries, android.R.layout.simple_spinner_item);
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
    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public void saveContactToDB(){
        User user = new User(mFirstNameEditText.getText().toString(),mLastNameEditText.getText().toString(),mEmailNameEditText.getText().toString(),
                mPhoneNumberEditText.getText().toString(),mCountrySpinner.getSelectedItem().toString(),mGender);
        mDB.insertIntoDatabase(user.toString(), mContext);

    }

    public interface popUpListener{
        public void onSave(DialogFragment dialog);
        public void onCancel(DialogFragment dialog);

    }
    popUpListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (popUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(" must implement NoticeDialogListener");
        }
    }

}
