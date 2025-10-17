package com.library.library_management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanDTO {
    private Long id;
    private Long usuarioId;
    private Long livroId;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String status;
}
