package edu.pucmm.controllers;

import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

/**
 * Created by Jhon on 7/6/2017.
 */
public class CommentDao extends GestionDb<Comment> {
    private static CommentDao instance;

    private CommentDao() {
        super(Comment.class);
    }

    public static CommentDao getInstance(){
        if (instance ==null){
            instance = new CommentDao();
        }
        return instance;
    }
}
