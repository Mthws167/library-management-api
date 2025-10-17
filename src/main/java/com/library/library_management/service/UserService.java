package com.library.library_management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User criar(User user) {
        if (user.getDataCadastro().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dataCadastro não pode ser maior que hoje");
        }
        return userRepository.save(user);
    }

    public User atualizar(Long id, User user) {
        User existente = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existente.setNome(user.getNome());
        existente.setTelefone(user.getTelefone());
        existente.setEmail(user.getEmail());
        return userRepository.save(existente);
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public void deletar(Long id) {
        userRepository.deleteById(id);
    }
}
