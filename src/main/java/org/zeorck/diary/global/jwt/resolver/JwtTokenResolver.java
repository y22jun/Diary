package org.zeorck.diary.global.jwt.resolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zeorck.diary.global.jwt.config.TokenProperties;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtTokenResolver {

    private static final String REPLACE_BEARER_PATTERN = "^Bearer( )*";
    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer .*");

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    private final SecretKey secretKey;

    public JwtTokenResolver(TokenProperties tokenProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProperties.secretKey()));
    }

    public Optional<String> resolveTokenFromRequest(HttpServletRequest request) {
        return resolveFromHeader(request)
                .or(() -> resolveFromCookie(request, ACCESS_TOKEN));
    }

    public Optional<String> resolveRefreshTokenFromRequest(HttpServletRequest request) {
        return resolveFromCookie(request, REFRESH_TOKEN);
    }

    public String getSubjectFromToken(String token) {
        return getClaims(token, secretKey)
                .getPayload()
                .getSubject();
    }

    private static Optional<String> resolveFromHeader(HttpServletRequest request) {
        Iterator<String> authorizations = request.getHeaders(AUTHORIZATION).asIterator();

        return Optional.ofNullable(authorizations)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .filter(auth -> StringUtils.hasText(auth) && BEARER_PATTERN.matcher(auth).matches())
                .map(auth -> auth.replaceAll(REPLACE_BEARER_PATTERN, ""));
    }

    private Optional<String> resolveFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), cookieName))
                .map(Cookie::getValue)
                .findFirst();
    }

    private Jws<Claims> getClaims(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
