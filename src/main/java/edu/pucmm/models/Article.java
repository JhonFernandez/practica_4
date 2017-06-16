package edu.pucmm.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Jhon on 7/6/2017.
 */
@Entity

public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private Date releaseDate;


    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private List<Comment> commentList;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tagList;

    public Article() {
    }

    public Article(String title, String body, Date releaseDate, User author) {
        this.title = title;
        this.body = body;
        this.releaseDate = releaseDate;
        this.author = author;
    }

    public Article(String title, String body, Date releaseDate, User author, List<Tag> tagList) {
        this.title = title;
        this.body = body;
        this.releaseDate = releaseDate;
        this.author = author;
        this.tagList = tagList;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", releaseDate=" + releaseDate +
                ", author=" + author +
                '}';
    }
}
