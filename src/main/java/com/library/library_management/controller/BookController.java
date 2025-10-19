package com.library.library_management.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.library.library_management.dto.BookDTO;
import com.library.library_management.dto.CreateBookDTO;
import com.library.library_management.dto.GoogleBookDTO;
import com.library.library_management.model.Book;
import com.library.library_management.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Value("${google.books.api-url}")
    private String googleApiUrl;

    @Value("${google.books.api-key}")
    private String googleApiKey;

    public BookController(BookService bookService, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/search")
    public ResponseEntity<List<GoogleBookDTO>> searchGoogleBooks(@RequestParam String title) {
        String url = googleApiUrl + "?q=intitle:" + title + "&key=" + googleApiKey;
        GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);
        List<GoogleBookDTO> books = response.getItems().stream()
            .map(item -> {
                GoogleBookDTO dto = new GoogleBookDTO();
                dto.setTitle(item.getVolumeInfo().getTitle());
                dto.setAuthors(item.getVolumeInfo().getAuthors());
                dto.setIsbn(item.getVolumeInfo().getIndustryIdentifiers().stream()
                    .filter(id -> "ISBN_13".equals(id.getType()))
                    .findFirst()
                    .map(Identifier::getIdentifier)
                    .orElse(null));
                dto.setPublishedDate(item.getVolumeInfo().getPublishedDate());
                dto.setCategories(item.getVolumeInfo().getCategories() != null ? String.join(", ", item.getVolumeInfo().getCategories()) : null);
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookDTO> criar(@RequestBody CreateBookDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        Book saved = bookService.criar(book);
        return ResponseEntity.ok(modelMapper.map(saved, BookDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> listar() {
        return ResponseEntity.ok(bookService.listarTodos().stream().map(b -> modelMapper.map(b, BookDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(bookService.buscarPorId(id), BookDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> atualizar(@PathVariable Long id, @RequestBody CreateBookDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        Book updated = bookService.atualizar(id, book);
        return ResponseEntity.ok(modelMapper.map(updated, BookDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bookService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

class GoogleBooksResponse {
    private List<GoogleBookItem> items;

    public List<GoogleBookItem> getItems() {
        return items;
    }

    public void setItems(List<GoogleBookItem> items) {
        this.items = items;
    }
}

class GoogleBookItem {
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}

class VolumeInfo {
    private String title;
    private List<String> authors;
    private String publishedDate;
    private List<String> categories;
    private List<Identifier> industryIdentifiers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Identifier> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<Identifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }
}

class Identifier {
    private String type;
    private String identifier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}