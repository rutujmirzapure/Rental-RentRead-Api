package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.Book;
import com.practice.Rentread.Exceptions.BookNotAvailableException;
import com.practice.Rentread.Exceptions.BookNotFoundException;
import com.practice.Rentread.Exceptions.RentalUserRentCapacityFullException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface BookService {

    Book addBook(Book book);
    Book getBookById(Long bookId) throws BookNotFoundException;
    List<Book> getAllBooks();
    Book updateBookById(Book book, Long bookId);
    void deleteBookById(Long bookId)throws BookNotFoundException;
    boolean checkisBookAvaliable(Long bookId) throws BookNotFoundException;
    void rentBook(Long bookId, String email) throws BookNotAvailableException, RentalUserRentCapacityFullException, UsernameNotFoundException, BookNotFoundException;
    void returnBook(Long bookId, String email) throws UsernameNotFoundException, BookNotFoundException;

}
