package com.example.userloginsqlite;

public class users {
    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private int age;
    private String bio;

    // Constructor for creating a new user (used during registration)
    public users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Constructor for updating a user (excludes id and password)
    public users(String name, String email, String gender, int age, String bio) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
    }


    // Constructor with all attributes
    public users(int id, String name, String email, String password, String gender, int age, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
    }
    public users(int id, String name, String email, String gender, int age, String bio){
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
    }

    // Getters and setters for all attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
