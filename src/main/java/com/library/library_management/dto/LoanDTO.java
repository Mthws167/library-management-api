package com.library.library_management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanDTO {
    private Long id;
    private Long usuarioId;
    private Long livroId;
    private String nomeUsuario;
    private String email;
    private String telefone;
    private String nomeLivro;
    private String autorLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String status;
}