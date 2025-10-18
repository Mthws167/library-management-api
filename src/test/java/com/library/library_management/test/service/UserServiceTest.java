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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;
import com.library.library_management.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setNome("Test User");
        user.setEmail("test@example.com");
        user.setTelefone("123456789");
        user.setData_cadastro(LocalDate.now());
    }

    @Test
    void testCriar() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        User result = userService.criar(user);

        verify(userRepository, times(1)).save(any(User.class));
        assert result.getId().equals(1L);
    }

    @Test
    void testAtualizar() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = new User();
        updatedUser.setNome("Updated User");
        updatedUser.setEmail("new@example.com");
        updatedUser.setTelefone("987654321");
        updatedUser.setData_cadastro(LocalDate.now());

        User result = userService.atualizar(1L, updatedUser);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        assert result.getNome().equals("Updated User");
    }

    @Test
    void testListarTodos() {
        when(userRepository.findAll()).thenReturn(java.util.Collections.singletonList(user));

        List<User> result = userService.listarTodos();

        verify(userRepository, times(1)).findAll();
        assert result.size() == 1;
    }

    @Test
    void testDeletar() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deletar(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}