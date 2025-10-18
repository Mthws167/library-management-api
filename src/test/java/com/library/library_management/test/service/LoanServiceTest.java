package com.library.library_management.test.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;
import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.LoanRepository;
import com.library.library_management.repository.UserRepository;
import com.library.library_management.service.LoanService;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanService loanService;

    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setNome("Test User");

        book = new Book();
        book.setId(1L);
        book.setTitulo("Test Book");

        loan = new Loan();
        loan.setId(1L);
        loan.setUsuario(user);
        loan.setLivro(book);
        loan.setData_emprestimo(LocalDate.now());
        loan.setStatus("ACTIVE");
    }

    @Test
    void testCriar() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.findByLivroIdAndStatus(1L, "ACTIVE")).thenReturn(Optional.empty());
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.criar(1L, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).findByLivroIdAndStatus(1L, "ACTIVE");
        verify(loanRepository, times(1)).save(any(Loan.class));
        assert result.getId().equals(1L);
    }

    @Test
    void testAtualizarStatus() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.atualizarStatus(1L, "RETURNED", LocalDate.now());

        verify(loanRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).save(any(Loan.class));
        assert result.getStatus().equals("RETURNED");
    }

    @Test
    void testListarPorUsuario() {
        when(loanRepository.findByUsuarioId(1L)).thenReturn(java.util.Collections.singletonList(loan));

        List<Loan> result = loanService.listarPorUsuario(1L);

        verify(loanRepository, times(1)).findByUsuarioId(1L);
        assert result.size() == 1;
    }

    @Test
    void testListarTodos() {
        when(loanRepository.findAll()).thenReturn(java.util.Collections.singletonList(loan));

        List<Loan> result = loanService.listarTodos();

        verify(loanRepository, times(1)).findAll();
        assert result.size() == 1;
    }
}