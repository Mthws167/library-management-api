package com.library.library_management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
        Pattern.CASE_INSENSITIVE
    );

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User criar(User user) {
        validarEmail(user.getEmail());

        if (user.getData_cadastro() == null) {
            user.setData_cadastro(LocalDate.now());
        } else if (user.getData_cadastro().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dataCadastro não pode ser maior que hoje");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
        }

        return userRepository.save(user);
    }

    public User atualizar(Long id, User user) {
        User existente = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        validarEmail(user.getEmail());

        // Verifica duplicidade apenas se o e-mail foi alterado
        if (!existente.getEmail().equalsIgnoreCase(user.getEmail()) &&
            userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
        }

        existente.setNome(user.getNome());
        existente.setTelefone(user.getTelefone());
        existente.setEmail(user.getEmail());
        return userRepository.save(existente);
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public void deletar(Long id) {
        userRepository.deleteById(id);
    }

    // 🔍 Método auxiliar de validação de formato
    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de e-mail inválido");
        }
    }
}
