package com.example.expirymate;

public class NewUser {

    private String customerid;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String emailid;
    private String phonenum;
    private String gender;
    private String address;

    public NewUser()
    {
    }

    public NewUser(String customerid, String firstname, String lastname, String username, String password, String emailid,
                   String phonenum, String gender, String address)
    {
        this.customerid=customerid;
        this.firstname=firstname;
        this.lastname=lastname;
        this.username=username;
        this.password=password;
        this.emailid=emailid;
        this.phonenum=phonenum;
        this.address = address;
        this.setGender(gender);
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
