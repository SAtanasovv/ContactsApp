package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.PopUpDialog;
import com.satanasov.contactsapp.R;

import java.util.List;

public class MainActivity extends FragmentActivity implements PopUpDialog.popUpListener {
    private                          AlertDialog                     mDialog;
    private                          DataBase                        mDB;
    private                          Context                         mContext;
    private                          List                            mUserContacts;
    private                          FloatingActionButton            fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDB = new DataBase();
        mContext = this;
        mUserContacts = mDB.readFromFile(mContext);
        isContactsEmpty();
        fab = findViewById(R.id.floating_button_main_id);

    }
    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpDialog popUp = new PopUpDialog();
                popUp.showPopUp(getSupportFragmentManager(),"PopUpActivity", this);

            }
        });
    }


    public void isContactsEmpty() {
        if (!mUserContacts.isEmpty()) {
            startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            finish();
        }

    }



    @Override
    public void onCancel(DialogFragment dialog) {

    }
//    public void handle() {
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mDialog.dismiss();
//                startActivity(new Intent(MainActivity.this, ContactListActivity.class));
//                finish();
//            }
//        }, 1200); //  1 second.
//    }

    @Override
    public void onSave(DialogFragment dialog) {
        startActivity(new Intent(MainActivity.this, ContactListActivity.class));
        finish();
    }

    public void saveContactFromPopUp(){}


}