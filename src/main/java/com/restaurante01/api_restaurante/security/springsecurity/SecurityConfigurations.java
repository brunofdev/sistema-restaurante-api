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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/usuarios/cadastro",
            "/usuarios/cadastro-admin1",
            "/usuarios/cadastro-admin3",
            "/usuarios/obter-todos",
            "/api/auth/login",
            "/auth/login", // Adicionei este caso a URL mude
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/ws/**"
    };

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
                // 1. AQUI ESTÁ A CORREÇÃO: Ativa o CORS usando nossa configuração bean
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // --- MUDE ISTO ---
        // DE: configuration.setAllowedOrigins(List.of("*"));
        // PARA:
        configuration.setAllowedOriginPatterns(List.of("*")); // Usa Padrão em vez de Fixo
        configuration.setAllowCredentials(true); // <--- IMPORTANTE: Permite cookies/auth
        // -----------------

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}