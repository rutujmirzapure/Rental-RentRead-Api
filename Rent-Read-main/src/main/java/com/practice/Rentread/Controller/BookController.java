package com.practice.Rentread.Controller;

import com.practice.Rentread.DataModels.Book;
import com.practice.Rentread.DataModels.Email;
import com.practice.Rentread.Exceptions.BookNotAvailableException;
import com.practice.Rentread.Exceptions.BookNotFoundException;
import com.practice.Rentread.Exceptions.RentalUserRentCapacityFullException;
import com.practice.Rentread.Service.BookService;
import com.practice.Rentread.dto.ErrorResponse;
import com.practice.Rentread.dto.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks(){
        return new ResponseEntity(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id){
        try{
            Book book = bookService.getBookById(id);
            return new ResponseEntity(book,HttpStatus.OK);
        }catch(BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addNewBook(@RequestBody Book book){
        return  new ResponseEntity(bookService.addBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        try{
            bookService.deleteBookById(id);
            return new ResponseEntity(new SuccessMessage("Book Deleted Successfully"), HttpStatus.OK);
        }catch(BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBookById(@RequestBody Book book, @PathVariable Long id){
        try{
            Book updatedBook = bookService.updateBookById(book, id);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }catch(BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<?> bookAvailability(@PathVariable Long id){
        try{
            if(bookService.checkisBookAvaliable(id)) return new ResponseEntity(new SuccessMessage("Book is Available"), HttpStatus.OK);
            return new ResponseEntity(new ErrorResponse("Book is not available"), HttpStatus.BAD_REQUEST);
        }catch(BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/library/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @RequestBody Email email){
        try{
            bookService.returnBook(bookId, email.getEmail());
            return new ResponseEntity(new SuccessMessage("Book returned SuccessFully"), HttpStatus.OK);
        }catch(UsernameNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }catch(BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

        @PostMapping("/library/{bookId}/rent")
    public ResponseEntity<?> rentBook(@PathVariable Long bookId, @RequestBody Email email){
        try{
            bookService.rentBook(bookId, email.getEmail());
            return new ResponseEntity( new SuccessMessage("Book rented successfully"), HttpStatus.OK);

        }catch(BookNotAvailableException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }catch(RentalUserRentCapacityFullException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }catch(UsernameNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (BookNotFoundException e){
            log.error(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


}
