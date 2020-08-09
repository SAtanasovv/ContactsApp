package com.satanasov.contactsapp.RecyclerViewAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satanasov.contactsapp.LocalDB.DataBase;
import com.satanasov.contactsapp.Model.User;
import com.satanasov.contactsapp.R;
import com.satanasov.contactsapp.View.DetailsActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
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
        holder.fullName.setText(user.getFirstName()+" "+user.getLastName());
        holder.phoneNumber.setText(user.getPhoneNumber());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView fullName;
        public TextView phoneNumber;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context=ctx;
            fullName= view.findViewById(R.id.fullNameTextViewID);
            phoneNumber=view.findViewById(R.id.ponenumberTextViewID);
            editButton= view.findViewById(R.id.editButtonID);
            deleteButton = view.findViewById(R.id.deleteButtonID);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    User user = userList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("fullName",user.getFirstName()+" "+user.getLastName());
                    intent.putExtra("phoneNumber",user.getPhoneNumber());
                    intent.putExtra("email",user.getEmail());
                    intent.putExtra("country",user.getCountry());
                    intent.putExtra("gender",user.getGender());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.editButtonID:
                    break;
                case R.id.deleteButtonID:
                    int position =getAdapterPosition();
                    User user =userList.get(position);
                    deleteContact(user);
                    break;
            }
        }

        public void deleteContact(final User user){
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog,null);
            Button cancelButton = view.findViewById(R.id.cancelButtonConfirmationDialogID);
            Button yesButton = view.findViewById(R.id.yesButtonConfirmationDialogID);

            alertDialogBuilder.setView(view);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBase db = new DataBase();
                    userList.remove(user);
                    db.deleteDatabase(context);
                    for(User user : userList){
                        db.insertIntoDatabase(user.toString(),context);
                    }
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();

                }
            });

        }
    }
}
