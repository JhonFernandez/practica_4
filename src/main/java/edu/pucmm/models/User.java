package edu.pucmm.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhon on 7/6/2017.
 */
@Entity
public class User implements Serializable {
    @Id
    private String userName;
    private String name;
    private String password;
    private Boolean isAdmin;
    private Boolean isAuthor;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Article> articleList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Valoracion> valoraciones;

    public User() {
    }

    public User(String userName, String name, String password, Boolean isAdmin, Boolean isAuthor) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isAuthor = isAuthor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getAuthor() {
        return isAuthor;
    }

    public void setAuthor(Boolean author) {
        isAuthor = author;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", isAuthor=" + isAuthor +
                '}';
    }
}
