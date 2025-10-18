package com.library.library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management.model.Book;
import com.library.library_management.repository.BookRepository;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book criar(Book book) {
        return bookRepository.save(book);
    }

    public Book atualizar(Long id, Book book) {
        Book existente = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        existente.setTitulo(book.getTitulo());
        existente.setAutor(book.getAutor());
        existente.setIsbn(book.getIsbn());
        existente.setCategoria(book.getCategoria());
        existente.setData_publicacao(book.getData_publicacao());
        return bookRepository.save(existente);
    }

    public Book buscarPorId(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public List<Book> listarTodos() {
        return bookRepository.findAll();
    }

    public void deletar(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> recomendarPorCategoria(Long usuarioId, List<Long> jaEmprestadosIds, String categoria) {
        // Retorna livros da mesma categoria e que não estejam na lista de ids já emprestados
        return bookRepository.findByCategoriaAndIdNotIn(categoria, jaEmprestadosIds);
    }
}