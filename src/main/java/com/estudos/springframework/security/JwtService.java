package com.estudos.springframework.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${security.token.secret}")
    private String SECRET_KEY;
    private static final String API_ISSUER = "anime-api";

    public String generateToken(String subject) {
        try {
            return JWT.create()
                    .withIssuer(API_ISSUER)
                    .withSubject(subject)
                    .withExpiresAt(generateExpiration())
                    .sign(Algorithm.HMAC256(SECRET_KEY));
        } catch (JWTCreationException e) {
            // TODO
            throw new RuntimeException("Erro no JWT");
        }
    }

    public String getSubjectFromToken(String token) throws JWTVerificationException{
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(API_ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }


    private Instant generateExpiration(){
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.UTC);
    }

}
