package com.example.vacancy_search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Пути, доступные для всех
                        .requestMatchers("/signin", "/signup", "/signup/employer", "/vacancy/find").permitAll() // Доступ для всех
                        // Пути, доступные только для кандидатов
                        .requestMatchers("/vacancy/find","/employer/{id}").hasRole("CANDIDATE") // Доступ только для кандидатов
                        // Пути, доступные только для работодателей
                        .requestMatchers("/vacancy/create").hasRole("EMPLOYER") // Доступ только для работодателей
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signin") // Страница входа
                        .loginProcessingUrl("/signin")
                        .defaultSuccessUrl("/vacancy/find", true) // Перенаправление после успешного входа
                        .failureUrl("/signin?error=true") // Перенаправление при ошибке входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessUrl("/signin") // Перенаправление после выхода
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }
}