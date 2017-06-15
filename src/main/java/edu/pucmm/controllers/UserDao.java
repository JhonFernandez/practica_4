package edu.pucmm.controllers;

import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by Jhon on 7/6/2017.
 */
public class UserDao  extends GestionDb<User> {
    private static UserDao instance;

    private UserDao() {
        super(User.class);
    }

    public static UserDao getInstance(){
        if (instance ==null){
            instance = new UserDao();
        }
        return instance;
    }


}
