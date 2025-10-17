package com.library.library_management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private String telefone;
}
