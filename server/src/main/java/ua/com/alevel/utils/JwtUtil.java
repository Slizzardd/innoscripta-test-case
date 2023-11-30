package ua.com.alevel.utils;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Utility class for working with JWT (JSON Web Token).
 */
public final class JwtUtil {
    private static final String SECRET_KEY = "gffdsgbkjjdsfgjgjakBGKdgfdgdfgstrhGFGfdgGSGSDGSDFSBDJFNSDLFsnajlkfknsdjfnASLKFDSNDJBAJFBSJKDNFjnknKFNKSNDFK";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours in milliseconds


    private JwtUtil() {
        throw new IllegalStateException("This is a utility class");
    }

    public static String extractUsername(String token) {
        if ("null".equals(token)) {
            throw new JwtException("Failed to parse JWT token");
        }
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException("Failed to parse JWT token", e);
        }
    }

    public static String generateJwtToken(String username) {
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            throw new JwtException("Failed to generate JWT token", e);
        }
    }

    public static boolean authCheck(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractToken(String fullJwtToken) throws IllegalArgumentException {
        if (StringUtils.startsWithIgnoreCase(fullJwtToken, "Bearer ")) {
            return fullJwtToken.substring("Bearer ".length());
        } else {
            throw new IllegalArgumentException("Incorrect token. The token must be non-empty and start with 'Bearer '.");
        }
    }
}
