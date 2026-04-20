package com.restaurante01.api_restaurante.infraestrutura.jwt;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String chaveSecreta;

    @Value("${jwt.expiration.ms}")
    private long tempoExpiracaoMs;

    public String gerarToken(String cpf, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + tempoExpiracaoMs);

        return Jwts.builder()
                .claims(claims)
                .subject(cpf)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getChaveAssinatura(), Jwts.SIG.HS256)
                .compact();
    }

    public String extrairCpf(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public boolean tokenValido(String token, String cpf) {
        final String cpfDoToken = extrairCpf(token);
        return cpfDoToken.equals(cpf) && !tokenExpirado(token);
    }

    public String validarToken(String token) {
        try {
            return extrairCpf(token);
        } catch (Exception e) {
            return null;
        }
    }
    public String extrairRole(String token) {
        return extrairClaim(token, claims -> claims.get("role", String.class));
    }

    // --- Auxiliares ---

    private boolean tokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    private Date extrairExpiracao(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }

    private <T> T extrairClaim(String token, Function<Claims, T> resolverClaim) {
        return resolverClaim.apply(extrairTodosClaims(token));
    }

    private Claims extrairTodosClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getChaveAssinatura() {
        byte[] bytesChave = Decoders.BASE64.decode(chaveSecreta);
        return Keys.hmacShaKeyFor(bytesChave);
    }
}