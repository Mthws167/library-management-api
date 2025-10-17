package com.library.library_management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateBookDTO {
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    private String categoria;
}
