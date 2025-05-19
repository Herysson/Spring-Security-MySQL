package com.herysson.springsecuritymysql.service;

import com.herysson.springsecuritymysql.model.User;
import com.herysson.springsecuritymysql.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// A anotação @Service indica que esta classe é um componente de serviço gerenciado pelo Spring.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repositório para buscar os dados do usuário no banco de dados.
    private final UserRepository userRepository;

    // Construtor para injetar o repositório de usuários.
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método que carrega os detalhes do usuário com base no nome de usuário.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo nome de usuário.
        // Lança uma exceção UsernameNotFoundException se o usuário não for encontrado.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Retorna uma implementação de UserDetails com as informações do usuário.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // Nome de usuário.
                user.getPassword(), // Senha criptografada.
                // Concede uma autoridade baseada no papel do usuário (ex.: ROLE_USER ou ROLE_ADMIN).
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}