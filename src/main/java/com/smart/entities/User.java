package com.smart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
public class User {
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
    public List<Contact> contacts=new ArrayList<>();
    public List<Contact> getContacts(){
        return contacts;
    }
    public void setContacts(List<Contact> contacts){
        this.contacts=contacts;
    }
    public User(){

    }
    @jakarta.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int Id;
    @NotBlank(message="Name cannot be empty")
    @Size(min=2,max=20,message="Name between 2-20")
    private String name;
    @Column(unique = true)
    @NotBlank(message="Email shouldn't be empty")
    private String email;
    @NotBlank(message="Password shouldn't be empty")
    @Column(unique = true)
    private String password;
    private String role;
    private boolean enabled;
    private String imageURL;
    @Column(length=500)
    private String about;
}
