package com.smart.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Contact")
public class Contact {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Cid;
    private String Cname;
    @Column(unique=true)
    private String Cemail;
    private String CImageURL;
    private String Coccupation;
    private String Cnickname;
    private String CphoneNo;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCphoneNo() {
        return CphoneNo;
    }

    public void setCphoneNo(String cphoneNo) {
        CphoneNo = cphoneNo;
    }

    public String getCnickname() {
        return Cnickname;
    }

    public void setCnickname(String cnickname) {
        Cnickname = cnickname;
    }

    public String getCoccupation() {
        return Coccupation;
    }

    public void setCoccupation(String coccupation) {
        Coccupation = coccupation;
    }

    public String getCImageURL() {
        return CImageURL;
    }

    public void setCImageURL(String CImageURL) {
        this.CImageURL = CImageURL;
    }

    public String getCemail() {
        return Cemail;
    }

    public void setCemail(String cemail) {
        Cemail = cemail;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    @Column(length=500)
    private String description;

    public String getName() {
        return Cname;
    }
}
