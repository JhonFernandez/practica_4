package edu.pucmm.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jhon on 7/6/2017.
 */
@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    private Article article;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER)
    private List<Valoracion> valoraciones;

    public Comment() {
    }

    public Comment(String body, Article article, User author) {
        this.body = body;
        this.article = article;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Article getArticle() {
        return article;
    }

    public User getAuthor() {
        return author;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public int CountValoracion(int valoracion){
        int cont = 0;
        for (Valoracion val :valoraciones) {
            if (val.getValoracion() == valoracion){
                cont++;
            }
        }
        return cont;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", article=" + article +
                ", author=" + author +
                '}';
    }
}
