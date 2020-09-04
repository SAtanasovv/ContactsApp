package com.satanasov.contactsapp.LocalDB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.satanasov.contactsapp.Model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBase {
    private final String dataBaseFileName = "test12.txt";


    //INSERT INTO DATABASE
    public void insertIntoDatabase(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(dataBaseFileName, Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
           // Toast.makeText(context, "Save Successful", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    // READ FROM DATABASE
    public List readFromFile(Context context) {
        List<User> users = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput(dataBaseFileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Scanner input = new Scanner(inputStreamReader);

                while ((input.hasNext())) {

                    String firstName = input.next();
                    String lastName = input.next();
                    String email = input.next();
                    String phoneNumber = input.next();
                    String country =input.next();
                    String gender = input.next();

                    User user = new User(firstName, lastName, email,phoneNumber,country,gender);
                    users.add(user);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return users;
    }

    //REMOVE USER FROM DB
    public void deleteDatabase(Context context) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(dataBaseFileName, 0));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
       //Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
    }


}
