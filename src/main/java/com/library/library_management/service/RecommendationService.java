package com.library.library_management.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.library.library_management.dto.BookDTO;
import com.library.library_management.dto.RecommendationDTO;
import com.library.library_management.dto.UserDTO;
import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;

@Service
public class RecommendationService {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RecommendationService(LoanService loanService, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<RecommendationDTO> getRecommendationsForAllUsers() {
        List<User> users = userService.listarTodos();
        List<RecommendationDTO> recommendations = new ArrayList<>();

        for (User user : users) {
            RecommendationDTO dto = getRecommendationsForUser(user.getId());
            recommendations.add(dto);
        }

        return recommendations;
    }

    public RecommendationDTO getRecommendationsForUser(Long userId) {
        User user = userService.buscarPorId(userId);
        List<Loan> loans = loanService.listByUserAndStatus(userId);

        Set<String> categories = new HashSet<>();
        List<Long> borrowedBookIds = new ArrayList<>();

        for (Loan loan : loans) {
            Book book = loan.getLivro();
            categories.add(book.getCategoria());
            borrowedBookIds.add(book.getId());
        }

        List<Book> recommendedBooks = new ArrayList<>();
        for (String category : categories) {
            List<Book> recs = bookService.recomendarPorCategoria(userId, borrowedBookIds, category);
            recommendedBooks.addAll(recs);
        }

        RecommendationDTO dto = new RecommendationDTO();
        dto.setUser(modelMapper.map(user, UserDTO.class));
        dto.setRecommendedBooks(recommendedBooks.stream()
            .map(b -> modelMapper.map(b, BookDTO.class))
            .collect(Collectors.toList()));

        return dto;
    }
}