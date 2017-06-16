package edu.pucmm.models;

import javax.persistence.*;

/**
 * Created by Jhon on 16/6/2017.
 */
@Entity
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;

    private int valoracion;


    public Valoracion() {
    }

    public Valoracion(User user, Article article, int valoracion) {
        this.user = user;
        this.article = article;
        this.valoracion = valoracion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Valoracion{" +
                "user=" + user +
                ", article=" + article +
                ", valoracion=" + valoracion +
                '}';
    }
}
