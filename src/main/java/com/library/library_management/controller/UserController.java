package com.library.library_management.controller;

import com.library.library_management.dto.CreateUserDTO;
import com.library.library_management.dto.UserDTO;
import com.library.library_management.model.User;
import com.library.library_management.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> criar(@Validated @RequestBody CreateUserDTO dto) {
        User entity = modelMapper.map(dto, User.class);
        User saved = userService.criar(entity);
        return ResponseEntity.ok(modelMapper.map(saved, UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listar() {
        return ResponseEntity.ok(userService.listarTodos().stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(userService.buscarPorId(id), UserDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> atualizar(@PathVariable Long id, @RequestBody CreateUserDTO dto) {
        User entity = modelMapper.map(dto, User.class);
        User updated = userService.atualizar(id, entity);
        return ResponseEntity.ok(modelMapper.map(updated, UserDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
