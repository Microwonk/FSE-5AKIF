package net.microwonk.studentenverwaltung.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtTokenService {

    //private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(20);

    private final Algorithm hmac512;
    private final JWTVerifier verifier;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenService(@Value("${jwt.secret}") final String secret, @Value("${jwt.accesstoken.expiration}")long accessTokenExpiration, @Value("${jwt.refreshtoken.expiration}")long refreshTokenExpiration) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateAccessToken(final UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(this.accessTokenExpiration))
                .sign(this.hmac512);
    }

    public String generateRefreshToken(final UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(this.refreshTokenExpiration))
                .sign(this.hmac512);
    }

    public String validateTokenAndGetUsername(final String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            System.out.println("token invalid: " + verificationEx.getMessage());
            return null;
        }
    }

}
