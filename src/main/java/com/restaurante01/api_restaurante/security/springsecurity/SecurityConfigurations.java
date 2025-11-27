package com.restaurante01.api_restaurante.security.springsecurity;


import com.restaurante01.api_restaurante.usuarios.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    // 1. LISTA DE ROTAS PÚBLICAS (Array de Strings é melhor que List para o Spring)
    private static final String[] PUBLIC_ENDPOINTS = {
            "/usuarios/cadastro",
            "/usuarios/cadastro-admin1",
            "/usuarios/cadastro-admin3",
            "/usuarios/obter-todos",
            "/api/auth/login",
            "/v3/api-docs/**", // Swagger
            "/swagger-ui/**",  // Swagger
            "/swagger-ui.html",
            "/ws/**" // WebSocket liberado publicamente para testes

    };

    // 2. MAPA DE ROTAS PROTEGIDAS (URL -> Nível Mínimo de Acesso)
    // Dica: Use o Enum UserRole.ADMIN3, etc.
    private static final Map<String, UserRole> PROTECTED_ROUTES = Map.of(
            "/produtos/adicionar-produto", UserRole.USER,
            "/produtos/todos-produtos", UserRole.USER,
            "/pedido/criar-pedido", UserRole.USER,
            "/pedido/obter-todos-pedidos", UserRole.USER,
            "/pedido/*/status", UserRole.ADMIN1
    );
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {

                    authorize.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                    PROTECTED_ROUTES.forEach((url, role) -> {
                        authorize.requestMatchers(url).hasRole(role.name());
                    });
                authorize.anyRequest().hasRole("ADMIN3");
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todas as rotas
                        .allowedOrigins("*") // Libera para QUALQUER site (para testes)
                        // .allowedOrigins("http://localhost:63342") // Use assim em produção
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"); // <--- O PATCH PRECISA ESTAR AQUI
            }
        };
    }
}
