package edu.pucmm.controllers;


import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Jhon on 7/6/2017.
 */
public class TagDao extends GestionDb<Tag,Integer> {
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

    public Tag findByName(String name){
        for (Tag tag:findAll()) {
            if (tag.getName().equalsIgnoreCase(name))
            return tag;
        }
        return null;
    }
}
