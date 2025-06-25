package br.com.tjrj.biblioteca.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String segredo;

    @Value("${app.jwt.expiracao}")
    private Long expiracaoMillis;

    private Key chaveAssinatura;

    @PostConstruct
    public void init() {
        if (segredo.length() < 32) {
            throw new IllegalArgumentException("O segredo do JWT deve ter pelo menos 256 bits.");
        }
        this.chaveAssinatura = Keys.hmacShaKeyFor(segredo.getBytes());
    }

    public String gerarToken(String username, List<String> roles) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiracaoMillis);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(chaveAssinatura, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token inválido: {}", e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        Object rolesClaim = getClaims(token).get("roles");
        if (rolesClaim instanceof List<?> lista) {
            return lista.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chaveAssinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
