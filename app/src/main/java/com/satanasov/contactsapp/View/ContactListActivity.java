package com.satanasov.contactsapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;
import com.satanasov.contactsapp.RecyclerViewAdapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private DataBase db;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerAdapter;

    private List<User> usersContactsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        db= new DataBase();
        context=this;
        FloatingActionButton fab = findViewById(R.id.floatingButtonContactsID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        recyclerView = findViewById(R.id.contactListRecycleID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersContactsList = db.readFromFile(context);

        recyclerAdapter= new RecyclerViewAdapter(this,usersContactsList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();



    }
}