package com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.model.Book;
import com.bookstoreWithHibernatePostgre.bookstoreWithHibernatePostgre.repository.BookRepository;

//@CrossOrigin(origins ="http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {


    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

        @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
        try {
            List<Book> books = new ArrayList<Book>();

            if (title == null) {
                bookRepository.findAll().forEach(books::add);
            }
            // books?title="xx" -> xx is contained in title
            else {
                bookRepository.findByTitleContaining(title).forEach(books::add);
            }

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public  ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        try {
            Book _book = bookRepository
                    .save(new Book(book.getAuthor(), book.getTitle(), book.getIsbn(), book.getCovertype()));
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setAuthor(book.getAuthor());
            _book.setTitle(book.getTitle());
            _book.setIsbn(book.getIsbn());
            _book.setCovertype(book.getCovertype());
            return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {

        //Optional<Book> bookData = bookRepository.findById(id);

        //if (bookData.isPresent()) {
        try {
                bookRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<List<Book>> findByIsbn(@PathVariable("isbn") int isbn) {
        try {
            List<Book> books = bookRepository.findByIsbn(isbn);

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
