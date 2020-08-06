package com.satanasov.contactsapp.LocalDB;
import android.content.Context;
import android.util.Log;

import com.satanasov.contactsapp.Model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DataBase {


    //INSERT INTO DATABASE
    public void insertIntoDatabase(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("test.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {

                    String firstName=receiveString;
                    String lastName=receiveString;
                    String email=receiveString;
                    String phoneNumber=receiveString;

                    User user =  new User(firstName,lastName,email,phoneNumber);
                    users.add(user);
                }

                inputStream.close();
               // ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return users;
    }






//	//REMOVE USER FROM DB
//	public void removeUser(Waitress a) {
//		File txtFile = new File(database_file_location);
//		if (!txtFile.exists())
//			log("File doesn't exist");
//
//		InputStreamReader isReader;
//		try {
//			isReader = new InputStreamReader(new FileInputStream(txtFile), "UTF-8");
//
//			JsonReader myReader = new JsonReader(isReader);
//
//		   Type listType = new TypeToken<ArrayList<Waitress>>() {}.getType();
//           ArrayList<Waitress> users = new Gson().fromJson(myReader, listType);
//
//           for (Waitress user : users) {
//        	  if(user.name.equals(a.name) && user.pinCode.equals(a.pinCode)) {
//        		  users.remove(user);
//        		  insertIntoDatabase(gson.toJson(users));
//        		  log("USER: " + user.name + " " + user.pinCode + " removed from database");
//        		  break;
//        	  }
//           }
//
//
//		} catch (Exception e) {
//			log("error load cache from file " + e.toString());
//
//		}
//
//	}

    public void log(String string) {
        System.out.println(string);
    }

}
