package com.herysson.springsecuritymysql.configuration;

import com.herysson.springsecuritymysql.model.User;
import com.herysson.springsecuritymysql.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// A anotação @Component indica que esta classe é um componente gerenciado pelo Spring.
@Component
public class DataInitializer implements CommandLineRunner {

    // Repositório para interagir com a entidade User no banco de dados.
    private final UserRepository userRepository;

    // Encoder para criptografar as senhas dos usuários.
    private final PasswordEncoder passwordEncoder;

    // Construtor para injetar as dependências necessárias.
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método executado automaticamente ao iniciar a aplicação.
    @Override
    public void run(String... args) {
        // Verifica se o usuário "admin" já existe no banco de dados.
        if (userRepository.findByUsername("admin").isEmpty()) {
            // Cria um novo usuário com a role ADMIN.
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456")); // Criptografa a senha.
            admin.setRole("ADMIN");
            userRepository.save(admin); // Salva o usuário no banco de dados.
            System.out.println("Usuário admin criado.");
        }

        // Verifica se o usuário "user" já existe no banco de dados.
        if (userRepository.findByUsername("user").isEmpty()) {
            // Cria um novo usuário com a role USER.
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123456")); // Criptografa a senha.
            user.setRole("USER");
            userRepository.save(user); // Salva o usuário no banco de dados.
            System.out.println("Usuário user criado.");
        }
    }
}