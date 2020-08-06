package com.satanasov.contactsapp.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewAdapter( Context context,List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row,parent,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstName;
        public TextView lastName;
        public TextView phoneNumber;
        public TextView email;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context=ctx;
            firstName = view.findViewById(R.id.firstNameEditTextID);
            lastName = view.findViewById(R.id.lastNameEditTextID);
           // phoneNumber=view.findViewById(R.id.)
        }
    }
}
