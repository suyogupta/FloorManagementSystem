package com.floorplanmanagment.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private int id;
    private String name;
    private String password;


    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public User(){
        super();
    }
    public User(int id, String name, String password){
        super();
        this.password = password;
        this.name = name;
        this.id = id;
    }

}
