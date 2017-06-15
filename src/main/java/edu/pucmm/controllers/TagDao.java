package edu.pucmm.controllers;


import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

/**
 * Created by Jhon on 7/6/2017.
 */
public class TagDao extends GestionDb<Tag> {
    private static TagDao instance;

    private TagDao() {
        super(Tag.class);
    }

    public static TagDao getInstance(){
        if (instance ==null){
            instance = new TagDao();
        }
        return instance;
    }
}
