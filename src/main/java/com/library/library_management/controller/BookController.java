package com.library.library_management.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management.dto.BookDTO;
import com.library.library_management.dto.CreateBookDTO;
import com.library.library_management.model.Book;
import com.library.library_management.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<BookDTO> criar(@RequestBody CreateBookDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        Book saved = bookService.criar(book);
        return ResponseEntity.ok(modelMapper.map(saved, BookDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> listar() {
        return ResponseEntity.ok(bookService.listarTodos().stream().map(b -> modelMapper.map(b, BookDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(bookService.buscarPorId(id), BookDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bookService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
