package edu.pucmm.models;

import javax.persistence.*;
import java.io.Serializable;

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
