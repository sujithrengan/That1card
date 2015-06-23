package com.thehp.that1card;

/**
 * Created by HP on 09-06-2015.
 */
public class User {

    public String Email;
    public String Pass;
    public String FirstName;
    public String LastName;
    public String dob;
    public String uid;

    public void setloginparam(String email,String pass)
    {
        this.Email=email;
        this.Pass=pass;

    }
    public User(){
        this.uid="";
        this.Email="";
        this.FirstName="";
        this.LastName="";
       this.dob="";


    }
}
