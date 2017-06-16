package edu.pucmm.models;

import com.sun.istack.internal.Nullable;

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


    @Nullable
    @ManyToOne
    private Comment comment;


    private int valoracion;


    public Valoracion() {
    }

    public Valoracion(User user, Article article, int valoracion) {
        this.user = user;
        this.article = article;
        this.valoracion = valoracion;
    }

    public Valoracion(User user, Comment comment, int valoracion) {
        this.user = user;
        this.comment = comment;
        this.valoracion = valoracion;
    }

    public Integer getId() {
        return id;
    }


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
