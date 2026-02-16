package com.stockinsight.global.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    // 테스트용 임의의 키 (32글자 이상)
    private final String secret = "testSecretKey123456789012345678901234567890";
    private final long expirationTime = 3600000L; // 1시간

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secret, expirationTime);

    @Test
    @DisplayName("이메일 통한 JWT 토큰 생성 테스트")
    void createToken() {
        // given
        String email = "test@test.com";

        // when
        String token = jwtTokenProvider.createAccessToken(email);

        // then
        System.out.println("생성된 토큰: " + token);
        assertThat(token).isNotNull();
        assertThat(token.length()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JWT 토큰에서 이메일 추출 테스트")
    void parseToken() {
        // given
        String originalEmail = "test@test.com";
        String token = jwtTokenProvider.createAccessToken(originalEmail);

        // when
        String extractedEmail = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        // then
        assertThat(extractedEmail).isEqualTo(originalEmail);
    }
}