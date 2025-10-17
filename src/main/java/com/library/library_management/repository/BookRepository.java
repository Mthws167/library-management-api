package com.library.library_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.library_management.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategoria(String categoria);
    List<Book> findByCategoriaAndIdNotIn(String categoria, List<Long> ids);
}
