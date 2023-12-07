package com.floorplanmanagment.Project;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Integer>{
    
    public ArrayList<User> findAll();
}
