package jesus.loginSecurity.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jesus.loginSecurity.services.impl.UserDetailsServiceImpl;

public class TokenUtils {
    private static final String SECRET_KEY = "QL0jPl64e3/Tl0y/rQwarFrBlZjUXDTGT+QltIxlDeg=";
    private static final long TOKEN_EXPIRATION_SECONDS = 2_592_000L;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public TokenUtils(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public static String createToken(int userId, String username, String role) {
        long expirationTime = TOKEN_EXPIRATION_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("role", role);
        claims.put("username", username);

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        if (token == null) {
            return null;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String user = claims.get("username", String.class);
            System.out.println(user);
            String role = claims.get("role", String.class);

            GrantedAuthority authority = () -> role;

            return new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(authority));

        } catch (JwtException e) {

            return null;
        }

    }

    public static int getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        int id = claims.get("id", Integer.class);
        return id;
    }

    public static String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return token;
        } else {
            return null;
        }
    }
}
