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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
                PopUpActivity popUp = new PopUpActivity(mContext);
                popUp.showPopUp();

            }
        });
    }

    public void isContactsEmpty() {
        if (!mUserContacts.isEmpty()) {
            startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            finish();
        }

    }
    public void handle() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                startActivity(new Intent(MainActivity.this, ContactListActivity.class));
                finish();
            }
        }, 1200); //  1 second.
    }



}