package com.example.userloginsqlite;

public class users {

    private int id;
    private String firstname;
    private String lastname;
    private String emailaddress;
    private String password;
    private String confirmpassword;

    // Constructor with ID
    public users(int id, String firstname, String lastname, String emailaddress, String password, String confirmpassword) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailaddress = emailaddress;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    // Constructor without ID (for registration)
    public users(String firstname, String lastname, String emailaddress, String password, String confirmpassword) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailaddress = emailaddress;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
