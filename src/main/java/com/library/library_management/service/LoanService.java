package com.library.library_management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;
import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.LoanRepository;
import com.library.library_management.repository.UserRepository;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan criar(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        loanRepository.findByLivroIdAndStatus(bookId, "ACTIVE").ifPresent(l -> {
            throw new RuntimeException("Livro já está emprestado");
        });

        Loan loan = Loan.builder()
                .usuario(user)
                .livro(book)
                .data_emprestimo(LocalDate.now())
                .status("ACTIVE")
                .build();

        return loanRepository.save(loan);
    }

    public Loan atualizarStatus(Long loanId, String status, LocalDate dataDevolucao) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        if (loan.getData_emprestimo().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser maior que hoje");
        }
        loan.setStatus(status);
        loan.setData_devolucao(dataDevolucao);
        return loanRepository.save(loan);
    }

    public List<Loan> listarPorUsuario(Long userId) {
        return loanRepository.findByUsuarioId(userId);
    }

    public List<Long> livrosJaEmprestadosIdsPorUsuario(Long userId) {
        return loanRepository.findByUsuarioId(userId).stream().map(l -> l.getLivro().getId()).collect(Collectors.toList());
    }

    public List<Loan> listarTodos() {
        return loanRepository.findAll();
    }
}
