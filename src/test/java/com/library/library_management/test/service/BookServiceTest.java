package com.library.library_management.test.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.library_management.model.Book;
import com.library.library_management.repository.BookRepository;
import com.library.library_management.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitulo("Test Book");
        book.setAutor("Test Author");
        book.setIsbn("123-456");
        book.setCategoria("Fiction");
        book.setData_publicacao(LocalDate.now());
    }

    @Test
    void testCriar() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.criar(book);

        verify(bookRepository, times(1)).save(any(Book.class));
        assert result.getId().equals(1L);
    }

    @Test
    void testAtualizar() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitulo("Old Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        Book updatedBook = new Book();
        updatedBook.setTitulo("New Title");
        updatedBook.setAutor("New Author");
        updatedBook.setIsbn("789-101");
        updatedBook.setCategoria("Non-Fiction");
        updatedBook.setData_publicacao(LocalDate.now());

        Book result = bookService.atualizar(1L, updatedBook);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
        assert result.getTitulo().equals("New Title");
    }

    @Test
    void testListarTodos() {
        List<Book> books = Arrays.asList(book, new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.listarTodos();

        verify(bookRepository, times(1)).findAll();
        assert result.size() == 2;
    }

    @Test
    void testDeletar() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deletar(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}