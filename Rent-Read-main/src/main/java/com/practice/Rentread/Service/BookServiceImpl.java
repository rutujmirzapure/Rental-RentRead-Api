package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.Book;
import com.practice.Rentread.DataModels.RentalUser;
import com.practice.Rentread.Exceptions.BookNotAvailableException;
import com.practice.Rentread.Exceptions.BookNotFoundException;
import com.practice.Rentread.Exceptions.RentalUserRentCapacityFullException;
import com.practice.Rentread.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements  BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalUserService rentalUserService;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long bookId) throws BookNotFoundException{
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");
        return bookRepository.findById(bookId).get();
    }

    @Override
    public List<Book> getAllBooks() {
        Iterable<Book> booksIterable = bookRepository.findAll();
        List<Book> books = new ArrayList<>();
        for(Book book : booksIterable) books.add(book);
        return books;
    }

    @Override
    public Book updateBookById(Book book, Long bookId) {
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");
        Book bookToUpdate = bookRepository.findById(bookId).get();
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setAvailabilityStatus(book.isAvailabilityStatus());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setTitle(book.getTitle());
        return bookRepository.save(bookToUpdate);
    }

    @Override
    public void deleteBookById(Long bookId) throws BookNotFoundException {
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");
        bookRepository.deleteById(bookId);
    }

    @Override
    public boolean checkisBookAvaliable(Long bookId)throws BookNotFoundException{
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");
        Book book = bookRepository.findById(bookId).get();
        if(book.isAvailabilityStatus() == true)return true;
        return false;
    }

    @Override
    public void rentBook(Long bookId, String email) throws BookNotAvailableException, RentalUserRentCapacityFullException, UsernameNotFoundException, BookNotFoundException{
        if(rentalUserService.findUserByEmail(email).isEmpty()) throw new UsernameNotFoundException("RentalUser does not exist");
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");
        if(checkisBookAvaliable(bookId) == false) throw new BookNotAvailableException("Book is not available for rent");

        RentalUser rentalUser = rentalUserService.findUserByEmail(email).get();
        if(rentalUser.getBook1() != -1 && rentalUser.getBook2() != -1) throw new RentalUserRentCapacityFullException(email + " already has maximum book holding");
        Book book = bookRepository.findById(bookId).get();

        book.setAvailabilityStatus(false);
        updateBookById(book, book.getBookId());

        if(rentalUser.getBook1() == -1L) rentalUser.setBook1(book.getBookId());
        else if(rentalUser.getBook2() == -1L) rentalUser.setBook2(book.getBookId());

        rentalUserService.save(rentalUser);
    }

    @Override
    public void returnBook(Long bookId, String email)throws UsernameNotFoundException, BookNotFoundException{
        if(rentalUserService.findUserByEmail(email).isEmpty()) throw new UsernameNotFoundException("RentalUser does not exception");
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("Book Id not found");

        RentalUser rentalUser = rentalUserService.findUserByEmail(email).get();
        if(rentalUser.getBook1() == bookId) rentalUser.setBook1(-1L);
        else if(rentalUser.getBook2() == bookId) rentalUser.setBook2(-1L);

        Book book = bookRepository.findById(bookId).get();
        book.setAvailabilityStatus(true);
        updateBookById(book,bookId);
        rentalUserService.save(rentalUser);
    }
}
