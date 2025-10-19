package com.library.library_management.dto;

import java.util.List;

import lombok.Data;

@Data
public class GoogleBookDTO {
    private String title;
    private List<String> authors;
    private String isbn;
    private String publishedDate;
    private String categories;
}