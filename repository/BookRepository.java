package com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

    // Remember the naming convention to match..

    List<Book> findByIsbn(int isbn);

    List<Book> findByTitleContaining(String title);
}
