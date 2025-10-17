package com.library.library_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.library_management.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUsuarioId(Long usuarioId);
    List<Loan> findByLivroId(Long livroId);
    Optional<Loan> findByLivroIdAndStatus(Long livroId, String status);
    List<Loan> findByUsuarioIdAndStatus(Long usuarioId, String status);
}
