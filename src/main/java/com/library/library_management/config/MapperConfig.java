package com.library.library_management.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.library_management.dto.CreateBookDTO;
import com.library.library_management.dto.CreateUserDTO;
import com.library.library_management.dto.LoanDTO;
import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.typeMap(CreateBookDTO.class, Book.class)
            .addMappings(mapper -> mapper.map(src -> src.getDataPublicacao(), Book::setData_publicacao));

        modelMapper.typeMap(CreateUserDTO.class, User.class)
            .addMappings(mapper -> mapper.map(src -> src.getDataCadastro(), User::setData_cadastro));

        modelMapper.typeMap(Loan.class, LoanDTO.class)
            .addMappings(mapper -> mapper.map(src -> src.getId(), LoanDTO::setId))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getId(), LoanDTO::setUsuarioId))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getId(), LoanDTO::setLivroId))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getNome(), LoanDTO::setNomeUsuario))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getEmail(), LoanDTO::setEmail))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getTelefone(), LoanDTO::setTelefone))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getTitulo(), LoanDTO::setNomeLivro))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getAutor(), LoanDTO::setAutorLivro))
            .addMappings(mapper -> mapper.map(src -> src.getData_emprestimo(), LoanDTO::setDataEmprestimo))
            .addMappings(mapper -> mapper.map(src -> src.getData_devolucao(), LoanDTO::setDataDevolucao))
            .addMappings(mapper -> mapper.map(src -> src.getStatus(), LoanDTO::setStatus));

        return modelMapper;
    }
}
