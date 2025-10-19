package com.library.library_management.test.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.library.library_management.dto.BookDTO;
import com.library.library_management.dto.RecommendationDTO;
import com.library.library_management.dto.UserDTO;
import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;
import com.library.library_management.service.BookService;
import com.library.library_management.service.LoanService;
import com.library.library_management.service.RecommendationService;
import com.library.library_management.service.UserService;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private LoanService loanService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RecommendationService recommendationService;

    private User user;
    private Book book;
    private Loan loan;
    private UserDTO userDTO;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setNome("João Silva");
        user.setEmail("joao@example.com");
        user.setTelefone("(11)12345-6789");
        user.setData_cadastro(LocalDate.now());

        book = new Book();
        book.setId(1L);
        book.setTitulo("Test Book");
        book.setAutor("Test Author");
        book.setIsbn("123-456");
        book.setCategoria("Fiction");
        book.setData_publicacao(LocalDate.now());

        loan = new Loan();
        loan.setId(1L);
        loan.setUsuario(user);
        loan.setLivro(book);
        loan.setData_emprestimo(LocalDate.now());
        loan.setStatus("ACTIVE");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setNome("João Silva");
        userDTO.setEmail("joao@example.com");
        userDTO.setTelefone("(11)12345-6789");
        userDTO.setDataCadastro(LocalDate.now());

        bookDTO = new BookDTO();
        bookDTO.setId(2L);
        bookDTO.setTitulo("Recommended Book");
        bookDTO.setAutor("Another Author");
        bookDTO.setIsbn("789-101");
        bookDTO.setCategoria("Fiction");
        bookDTO.setDataPublicacao(LocalDate.now());
    }

    @Test
    void testGetRecommendationsForAllUsers() {
        List<User> users = Arrays.asList(user);
        List<Loan> loans = Arrays.asList(loan);
        List<Book> recommendedBooks = Arrays.asList(
            new Book(2L, "Recommended Book", "Another Author", "789-101", LocalDate.now(), "Fiction")
        );

        when(userService.listarTodos()).thenReturn(users);
        when(userService.buscarPorId(1L)).thenReturn(user);
        when(loanService.listByUserAndStatus(1L)).thenReturn(loans);
        when(bookService.recomendarPorCategoria(anyLong(), anyList(), eq("Fiction"))).thenReturn(recommendedBooks);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(recommendedBooks.get(0), BookDTO.class)).thenReturn(bookDTO);

        List<RecommendationDTO> result = recommendationService.getRecommendationsForAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        RecommendationDTO recommendation = result.get(0);
        assertEquals("joao@example.com", recommendation.getUser().getEmail());
        assertEquals(1, recommendation.getRecommendedBooks().size());
        assertEquals("Recommended Book", recommendation.getRecommendedBooks().get(0).getTitulo());
        assertEquals("Another Author", recommendation.getRecommendedBooks().get(0).getAutor());

        verify(userService, times(1)).listarTodos();
        verify(userService, times(1)).buscarPorId(1L);
        verify(loanService, times(1)).listByUserAndStatus(1L);
        verify(bookService, times(1)).recomendarPorCategoria(anyLong(), anyList(), eq("Fiction"));
        verify(modelMapper, times(1)).map(user, UserDTO.class);
        verify(modelMapper, times(1)).map(recommendedBooks.get(0), BookDTO.class);
    }

    @Test
    void testGetRecommendationsForAllUsers_NoLoans() {
        List<User> users = Arrays.asList(user);
        List<Loan> loans = Arrays.asList();

        when(userService.listarTodos()).thenReturn(users);
        when(userService.buscarPorId(1L)).thenReturn(user);
        when(loanService.listByUserAndStatus(1L)).thenReturn(loans);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        List<RecommendationDTO> result = recommendationService.getRecommendationsForAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        RecommendationDTO recommendation = result.get(0);
        assertEquals("joao@example.com", recommendation.getUser().getEmail());
        assertEquals(0, recommendation.getRecommendedBooks().size());

        verify(userService, times(1)).listarTodos();
        verify(userService, times(1)).buscarPorId(1L);
        verify(loanService, times(1)).listByUserAndStatus(1L);
        verify(bookService, times(0)).recomendarPorCategoria(anyLong(), anyList(), eq("Fiction"));
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }
}