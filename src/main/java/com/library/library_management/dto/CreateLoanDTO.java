package com.library.library_management.dto;

import lombok.Data;

@Data
public class CreateLoanDTO {
    private Long userId;
    private Long bookId;
}
