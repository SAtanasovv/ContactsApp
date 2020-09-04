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
    private                  List<User>             mUserList;
    private                  Context                mContext;
    private                  AlertDialog.Builder    mAlertDialogBuilder;
    private                  AlertDialog            mAlertDialog;
    private                  LayoutInflater         mInflater;

    public RecyclerViewAdapter(Context context, List<User> userList) {
        this.mUserList       = userList;
        this.mContext        = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        User user           = mUserList.get(position);
        holder.fullName.setText(user.getFirstName() + " " + user.getLastName());
        holder.phoneNumber.setText(user.getPhoneNumber());


    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public                  TextView        fullName;
        public                  TextView        phoneNumber;
        public                  Button          editButton;
        public                  Button          deleteButton;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            mContext            = ctx;
            fullName            = view.findViewById(R.id.full_name_listrow_id);
            phoneNumber         = view.findViewById(R.id.phonenumber_listrow_id);
            editButton          = view.findViewById(R.id.edit_button_listrow_id);
            deleteButton        = view.findViewById(R.id.delete_button_listrow_id);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            // Going to Details Activity
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToDetails();
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.edit_button_listrow_id:
                    break;
                case R.id.delete_button_listrow_id:
                    int position   = getAdapterPosition();
                    User user      = mUserList.get(position);
                    deleteContact(user);
                    break;
            }
        }

        public void deleteContact(final User user) {
            mAlertDialogBuilder     = new AlertDialog.Builder(mContext);
            mInflater               = LayoutInflater.from(mContext);
            View view               = mInflater.inflate(R.layout.confirmation_dialog, null);
            Button cancelButton     = view.findViewById(R.id.cancelBtn_delete_dialog_id);
            Button yesButton        = view.findViewById(R.id.yesBtn_delete_dialog_id);

            mAlertDialogBuilder.setView(view);
            mAlertDialog            = mAlertDialogBuilder.create();
            mAlertDialog.show();


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAlertDialog.dismiss();
                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBase db     = new DataBase();
                    mUserList.remove(user);
                    db.deleteDatabase(mContext);
                    for (User user : mUserList) {
                        db.insertIntoDatabase(user.toString(), mContext);
                    }
                    notifyItemRemoved(getAdapterPosition());
                    mAlertDialog.dismiss();

                }
            });

        }

        public void goToDetails() {
            int position            = getAdapterPosition();
            User user               = mUserList.get(position);
            Intent intent           = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("fullName", user.getFirstName() + " " + user.getLastName());
            intent.putExtra("phoneNumber", user.getPhoneNumber());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("country", user.getCountry());
            intent.putExtra("gender", user.getGender());
            mContext.startActivity(intent);
        }

    }
}
