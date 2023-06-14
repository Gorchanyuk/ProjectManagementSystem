package digital.design.management.system.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import digital.design.management.system.dto.file.FileConfirmDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@Slf4j
@Component
public class TokenForConfirmUploadFile {

    @Value("${jwt_secret}")
    private String secret;
    private final String subject = "File details";
    private final String issuer = "Management system";


    public String generateToken(String filename,UUID fileUid,  UUID projectUid, String tempDir, String hashcode){
        log.debug("Generate token for {}", filename);
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(30).toInstant());

        return JWT.create()
                .withSubject(subject)
                .withClaim("filename", filename)
                .withClaim("fileUid", fileUid.toString())
                .withClaim("projectUid", projectUid.toString())
                .withClaim("tempDir", tempDir)
                .withClaim("hashcode", hashcode)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public FileConfirmDTO validateTokenAndRetrieveClaim(String token) {
        JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        log.debug("Successfully validate token for {}", jwt.getClaim("filename").asString());
        return FileConfirmDTO.builder()
                .uid(UUID.fromString(jwt.getClaim("fileUid").asString()))
                .projectUid(UUID.fromString(jwt.getClaim("projectUid").asString()))
                .fileName(jwt.getClaim("filename").asString())
                .tempDir(jwt.getClaim("tempDir").asString())
                .hashcode(jwt.getClaim("hashcode").asString())
                .build();
    }
}
