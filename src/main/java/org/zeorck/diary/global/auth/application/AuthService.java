package org.zeorck.diary.global.auth.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.domain.MemberRepository;
import org.zeorck.diary.global.auth.domain.RefreshToken;
import org.zeorck.diary.global.auth.domain.RefreshTokenRepository;
import org.zeorck.diary.global.auth.dto.response.LoginResultResponse;
import org.zeorck.diary.global.jwt.config.TokenProperties;
import org.zeorck.diary.global.jwt.generator.JwtTokenGenerator;
import org.zeorck.diary.global.jwt.injector.TokenInjector;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProperties tokenProperties;
    private final PasswordEncoder passwordEncoder;
    private final TokenInjector tokenInjector;

    @Transactional
    public LoginResultResponse login(String email, String password, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(email);
        member.checkPassword(passwordEncoder, password);

        LoginResultResponse result = generateLoginResult(member);

        tokenInjector.injectTokensToCookie(result, response);

        return result;
    }

    @Transactional
    public void logout(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    private LoginResultResponse generateLoginResult(Member member) {
        Long memberId = member.getId();
        String accessToken = tokenGenerator.generateAccessToken(memberId);
        String refreshToken = tokenGenerator.generateRefreshToken(memberId);

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId)
                .orElse(RefreshToken.of(member.getId(), refreshToken, tokenProperties.expirationTime().refreshToken()));

        refreshTokenEntity.rotate(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginResultResponse(accessToken, refreshToken);
    }
}
