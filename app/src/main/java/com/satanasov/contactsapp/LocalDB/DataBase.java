package com.satanasov.contactsapp.LocalDB;
import android.content.Context;
import android.util.Log;

import com.satanasov.contactsapp.Model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBase {


    //INSERT INTO DATABASE
    public void insertIntoDatabase(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("test.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    // READ FROM DATABASE
    public ArrayList readFromFile(Context context) {
        ArrayList<User> users = new ArrayList<>();
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("test.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Scanner input = new Scanner(inputStreamReader);

                while ( ( input.hasNext()) ) {

                    String firstName=input.next();
                    String lastName=input.next();
                    String email=input.next();
                    String phoneNumber=input.next();

                    User user =  new User(firstName,lastName,email,phoneNumber);
                    users.add(user);
                }

                inputStream.close();

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return users;
    }
    public int getCount(List arrayList){
        int count = 0;
        for(int i=0;i<arrayList.size();i++){
            count++;
        }
            return count;
    }





//	//REMOVE USER FROM DB



}
