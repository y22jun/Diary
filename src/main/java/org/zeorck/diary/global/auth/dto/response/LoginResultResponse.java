package org.zeorck.diary.global.auth.dto.response;

public record LoginResultResponse(
        String accessToken,
        String refreshToken
) {
}
