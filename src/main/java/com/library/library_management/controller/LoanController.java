package com.library.library_management.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management.dto.CreateLoanDTO;
import com.library.library_management.dto.LoanDTO;
import com.library.library_management.model.Loan;
import com.library.library_management.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final ModelMapper modelMapper;

    public LoanController(LoanService loanService, ModelMapper modelMapper) {
        this.loanService = loanService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> criar(@RequestBody CreateLoanDTO dto) {
        Loan created = loanService.criar(dto.getUserId(), dto.getBookId());
        return ResponseEntity.ok(modelMapper.map(created, LoanDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> atualizarStatus(@PathVariable Long id, @RequestBody LoanDTO dto) {
        LocalDate dataDevolucao = dto.getDataDevolucao();
        Loan updated = loanService.atualizarStatus(id, dto.getStatus(), dataDevolucao);
        return ResponseEntity.ok(modelMapper.map(updated, LoanDTO.class));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDTO>> listarPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.listarPorUsuario(userId).stream().map(l -> modelMapper.map(l, LoanDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listarTodos() {
        List<Loan> loans = loanService.listarTodos();
        return ResponseEntity.ok(loans.stream().map(l -> modelMapper.map(l, LoanDTO.class)).collect(Collectors.toList()));
    }
}
