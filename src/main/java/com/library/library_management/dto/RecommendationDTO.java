package com.library.library_management.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecommendationDTO {
    private UserDTO user;
    private List<BookDTO> recommendedBooks;
}