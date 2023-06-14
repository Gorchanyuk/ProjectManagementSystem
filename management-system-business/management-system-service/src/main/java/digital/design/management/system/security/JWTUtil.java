package digital.design.management.system.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    private final String subject = "User details";
    private final String issuer = "Management system";


    public String generateToken(String username){
        log.debug("Generate token for {}", username);
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(1).toInstant());

        return JWT.create()
                .withSubject(subject)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String username = jwt.getClaim("username").asString();
        log.debug("Successfully validate token for {}", username);
        return username;
    }
}
