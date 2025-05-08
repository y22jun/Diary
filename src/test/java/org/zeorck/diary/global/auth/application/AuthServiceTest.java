package org.zeorck.diary.global.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.domain.MemberRepository;
import org.zeorck.diary.domain.member.presentation.exception.MemberNotFoundException;
import org.zeorck.diary.global.auth.domain.RefreshTokenRepository;
import org.zeorck.diary.global.auth.dto.response.LoginResultResponse;
import org.zeorck.diary.global.jwt.config.TokenProperties;
import org.zeorck.diary.global.jwt.generator.JwtTokenGenerator;
import org.zeorck.diary.global.jwt.injector.TokenInjector;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenGenerator tokenGenerator;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenProperties tokenProperties;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenInjector tokenInjector;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        when(tokenProperties.expirationTime())
                .thenReturn(new TokenProperties.ExpirationTime(3600L, 86400L));
    }

    @DisplayName("로그인 성공 시 토큰과 응답이 반환된다")
    @Test
    void loginSuccess() {
        Member mockMember = mock(Member.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(memberRepository.findByEmail(anyString())).thenReturn(mockMember);
        when(tokenGenerator.generateAccessToken(any())).thenReturn("access-token");
        when(tokenGenerator.generateRefreshToken(any())).thenReturn("refresh-token");
        when(refreshTokenRepository.findByMemberId(any())).thenReturn(Optional.empty());

        LoginResultResponse result = authService.login("test@example.com", "password", response);

        verify(memberRepository).findByEmail("test@example.com");
        verify(mockMember).checkPassword(passwordEncoder, "password");
        verify(tokenInjector).injectTokensToCookie(any(), eq(response));
        assertEquals("access-token", result.accessToken());
        assertEquals("refresh-token", result.refreshToken());
    }

    @DisplayName("이메일이 존재하지 않으면 MemberNotFoundException이 발생한다")
    @Test
    void loginInvalidEmail() {
        when(memberRepository.findByEmail(anyString())).thenThrow(new MemberNotFoundException());

        assertThrows(MemberNotFoundException.class, () ->
                authService.login("wrong@example.com", "password", mock(HttpServletResponse.class))
        );
    }

    @DisplayName("비밀번호가 일치하지 않으면 MemberNotFoundException이 발생한다")
    @Test
    void loginWrongPassword() {
        Member mockMember = mock(Member.class);
        when(memberRepository.findByEmail(anyString())).thenReturn(mockMember);
        doThrow(MemberNotFoundException.class).when(mockMember).checkPassword(any(), anyString());

        assertThrows(MemberNotFoundException.class, () ->
                authService.login("test@example.com", "wrong-password", mock(HttpServletResponse.class))
        );
    }

    @DisplayName("logout 호출 시 해당 memberId의 refreshToken이 삭제된다")
    @Test
    void logoutSuccess() {
        Long memberId = 1L;

        authService.logout(memberId);

        verify(refreshTokenRepository, times(1)).deleteByMemberId(memberId);
    }

}
