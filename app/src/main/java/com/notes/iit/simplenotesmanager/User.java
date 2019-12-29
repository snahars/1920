package com.notes.iit.simplenotesmanager;


public class User {
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public String gender;
    public String contactNo;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String password, String firstName, String lastName, String gender, String contactNo) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.contactNo = contactNo;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNo() {
        return contactNo;
    }
}