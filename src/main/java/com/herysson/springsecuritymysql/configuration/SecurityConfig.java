package com.herysson.springsecuritymysql.configuration;

import com.herysson.springsecuritymysql.service.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// A anotação @Configuration indica que esta classe contém configurações do Spring.
@Configuration
// A anotação @EnableWebSecurity habilita a segurança da web no projeto.
@EnableWebSecurity
public class SecurityConfig {

    // Define o filtro de segurança principal da aplicação.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configura as autorizações para diferentes URLs.
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público às páginas de login e aos arquivos CSS.
                        .requestMatchers("/login", "/css/**").permitAll()
                        // Restringe o acesso à URL "/adminpanel" apenas para usuários com a autoridade "ADMIN".
                        .requestMatchers("/adminpanel").hasAuthority("ADMIN")
                        // Exige autenticação para qualquer outra requisição.
                        .anyRequest().authenticated()
                )
                // Configura o formulário de login.
                .formLogin(form -> form
                        // Define a página de login personalizada (opcional).
                        .loginPage("/login")
                        // Redireciona para "/index" após o login bem-sucedido.
                        .defaultSuccessUrl("/index", true)
                        // Permite acesso público à página de login.
                        .permitAll()
                )
                // Configura o logout.
                .logout(logout -> logout
                        // Define a URL de logout e o método HTTP associado.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        // Redireciona para a página inicial após o logout.
                        .logoutSuccessUrl("/")
                );

        // Retorna a configuração do filtro de segurança.
        return http.build();
    }

    // Define o encoder de senhas para criptografar e verificar senhas.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura o provedor de autenticação usando o serviço de detalhes do usuário e o encoder de senhas.
    @Bean
    public DaoAuthenticationProvider authProvider(CustomUserDetailsService userDetailsService,
                                                  BCryptPasswordEncoder encoder) {
        // Cria uma instância do provedor de autenticação DAO.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Define o serviço de detalhes do usuário.
        authProvider.setUserDetailsService(userDetailsService);
        // Define o encoder de senhas.
        authProvider.setPasswordEncoder(encoder);
        // Retorna o provedor de autenticação configurado.
        return authProvider;
    }
}