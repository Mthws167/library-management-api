package com.library.library_management.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.library_management.dto.BookDTO;
import com.library.library_management.dto.CreateBookDTO;
import com.library.library_management.dto.CreateUserDTO;
import com.library.library_management.dto.LoanDTO;
import com.library.library_management.dto.UserDTO;
import com.library.library_management.model.Book;
import com.library.library_management.model.Loan;
import com.library.library_management.model.User;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CreateBookDTO.class, Book.class)
            .addMappings(mapper -> mapper.map(CreateBookDTO::getDataPublicacao, Book::setData_publicacao));

        modelMapper.typeMap(Book.class, BookDTO.class)
            .addMappings(mapper -> mapper.map(Book::getData_publicacao, BookDTO::setDataPublicacao));

        modelMapper.typeMap(CreateUserDTO.class, User.class)
            .addMappings(mapper -> mapper.map(CreateUserDTO::getDataCadastro, User::setData_cadastro));

        modelMapper.typeMap(User.class, UserDTO.class)
            .addMappings(mapper -> mapper.map(User::getData_cadastro, UserDTO::setDataCadastro));

        modelMapper.typeMap(Loan.class, LoanDTO.class)
            .addMappings(mapper -> mapper.map(Loan::getId, LoanDTO::setId))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getId(), LoanDTO::setUsuarioId))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getNome(), LoanDTO::setNomeUsuario))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getEmail(), LoanDTO::setEmail))
            .addMappings(mapper -> mapper.map(src -> src.getUsuario().getTelefone(), LoanDTO::setTelefone))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getId(), LoanDTO::setLivroId))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getTitulo(), LoanDTO::setNomeLivro))
            .addMappings(mapper -> mapper.map(src -> src.getLivro().getAutor(), LoanDTO::setAutorLivro))
            .addMappings(mapper -> mapper.map(Loan::getData_emprestimo, LoanDTO::setDataEmprestimo))
            .addMappings(mapper -> mapper.map(Loan::getData_devolucao, LoanDTO::setDataDevolucao))
            .addMappings(mapper -> mapper.map(Loan::getStatus, LoanDTO::setStatus));

        return modelMapper;
    }
}