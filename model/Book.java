package com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.model;

import javax.persistence.*;

@Entity
@Table(name= "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name= "author")
    private String author;

    @Column(name= "title")
    private  String title;

    @Column(name= "isbn")
    private int isbn;

    @Column(name= "covertype")
    private String covertype;

    public Book() {

    }


    public Book(String author, String title, int isbn, String covertype) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.covertype = covertype;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getCovertype() {
        return covertype;
    }

    public void setCovertype(String covertype) {
        this.covertype = covertype;
    }
}
