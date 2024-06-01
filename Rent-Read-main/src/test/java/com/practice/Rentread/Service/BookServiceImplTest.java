package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.Book;
import com.practice.Rentread.Exceptions.BookNotFoundException;
import com.practice.Rentread.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Title1", "Author1", "Genre1", true);
        book2 = new Book(2L, "Title2", "Author2", "Genre2", false);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals(book1, foundBook);
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
    }

    @Test
    void updateBookById_ShouldUpdateBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        book1.setTitle("Updated Title");
        Book updatedBook = bookService.updateBookById(book1, 1L);

        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
    }

    @Test
    void updateBookById_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBookById(book1, 1L));
    }

//    @BookControllerTest
//    void deleteBookById_ShouldDeleteBook_WhenBookExists() {
//        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
//
//        bookService.deleteBookById(1L);
//
//        verify(bookRepository, 1).deleteById(1L);
//    }

    @Test
    void deleteBookById_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(1L));
    }
}
