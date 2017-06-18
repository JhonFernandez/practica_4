package edu.pucmm.controllers;

import edu.pucmm.models.*;
import edu.pucmm.services.GestionDb;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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

    public List<Article> findAll(int init, int end){
        EntityManager em = getEntityManager();
        end = Math.min(end, (int)getCount());
        Query query = em.createQuery("SELECT a FROM Article a order by a.id desc ")
                .setFirstResult(init)
                .setMaxResults(end);

        List<Article> lista = query.getResultList();
        return lista;
    }

    public long getCount(){
        EntityManager em = getEntityManager();
        return (long)em.createQuery("SELECT count (a) FROM Article a").getSingleResult();
    }
}
