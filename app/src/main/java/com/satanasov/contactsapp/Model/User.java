package com.satanasov.contactsapp.Model;


public class User {
    private                      String      mFirstName;
    private                      String      mLastName;
    private                      String      mEmail;
    private                      String      mCountry;
    private                      String      mGender;
    private                      String      mPhoneNumber;



    public User(String firstName, String lastName, String email, String phoneNumber, String country, String gender) {
        this.mFirstName         = firstName;
        this.mLastName          = lastName;
        this.mEmail             = email;
        this.mCountry           = country;
        this.mGender            = gender;
        this.mPhoneNumber       = phoneNumber;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName         = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName          = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail             = email;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry          = country;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender           = gender;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber      = phoneNumber;
    }

    @Override
    public String toString() {
        return mFirstName + "\n" + mLastName + "\n" + mEmail + "\n" + mPhoneNumber + "\n" + mCountry + "\n" + mGender + "\n";
    }
}
