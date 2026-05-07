package com.restaurante01.api_restaurante.infraestrutura.security.springsecurity;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
import static java.util.Map.entry;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {


    SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    // ROTAS PUBLICAS
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/cliente-login",
            "/api/auth/operador-login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/produtos/todos-produtos",
            "/cardapio-produto/publico/**",
            "/cliente/cadastro",
            "/operador/cadastro", //teste apenas
            "/ws/**",
            "/h2-console/**",
    };

    // ROTAS PROTEGIDAS — ordem importa: rotas exatas devem vir antes de padrões com variáveis de path
    private static final List<Map.Entry<String, Role>> PROTECTED_ROUTES = List.of(
            // Apenas USER+
            entry("/pedido/cliente/**", Role.USER),

            // Apenas ADMIN1+
            entry("/produtos/adicionar-produto", Role.ADMIN1),
            entry("/cardapio-produto/operador/**", Role.ADMIN1),
            entry("/pedido/operador/*/status", Role.ADMIN1),
            entry("/pedido/operador/hoje", Role.ADMIN1),
            entry("/pedido/operador/top-produtos-vendidos", Role.ADMIN1),
            entry("/cupom/admin/**", Role.ADMIN1),

            // Apenas ADMIN2+
            entry("/pedido/operador/todos", Role.ADMIN2),
            entry("/operador/obter-todos", Role.ADMIN2),
            entry("/clientes/obter-todos", Role.ADMIN2),

            // Apenas ADMIN3 — rotas exatas antes dos padrões com {id}
            entry("/operador/deletar/{id}", Role.ADMIN3),
            entry("/nova-rota-ilimitada", Role.ADMIN3),
            entry("/operador/{id}", Role.ADMIN3)
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                    PROTECTED_ROUTES.forEach(e ->
                        authorize.requestMatchers(e.getKey()).hasRole(e.getValue().name())
                    );
                    authorize.anyRequest().hasRole("ADMIN3");
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowCredentials(true);
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