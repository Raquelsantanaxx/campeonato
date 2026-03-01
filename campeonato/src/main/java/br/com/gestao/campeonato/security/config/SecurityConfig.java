package br.com.gestao.campeonato.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // 🔓 ARQUIVOS ESTÁTICOS
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                        // 🔓 PÁGINAS PÚBLICAS (SOMENTE VISUALIZAÇÃO)
                        .requestMatchers(
                                "/", "/home",
                                "/login", "/cadastro"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/campeonatos").permitAll()

                        // 🔐 QUALQUER COISA EM /campeonatos/** EXIGE LOGIN
                        .requestMatchers("/campeonatos/**").authenticated()

                        // 🔐 RESTO DO SISTEMA EXIGE LOGIN
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
