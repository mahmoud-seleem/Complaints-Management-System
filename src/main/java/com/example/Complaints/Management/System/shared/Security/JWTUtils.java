package com.example.Complaints.Management.System.shared.Security;

import com.example.Complaints.Management.System.shared.Utils.KeyUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JWTUtils {

    private static final String SECRET_KEY = "MY-SUPER-SECRET-JWT-KEY-FOR-HASHING";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 60;
//  private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private final PrivateKey privateKey = KeyUtils.getPrivateKey();
    private final PublicKey publicKey = KeyUtils.getPublicKey();
    public JWTUtils() throws Exception {
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
