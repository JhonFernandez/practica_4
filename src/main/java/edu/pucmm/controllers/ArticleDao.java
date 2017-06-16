package edu.pucmm.controllers;

import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

/**
 * Created by Jhon on 7/6/2017.
 */
public class ArticleDao extends GestionDb<Article,Integer> {

    private static ArticleDao instance;

    private ArticleDao() {
        super(Article.class);
    }

    public static ArticleDao getInstance(){
        if (instance ==null){
            instance = new ArticleDao();
        }
        return instance;
    }

    public int CountValoration(Article article,int valoracion){
        int cont = 0;
        for (Valoracion val :article.getValoraciones()) {
            if (val.getValoracion() == valoracion){
                cont++;
            }
        }
        return cont;
    }

}
