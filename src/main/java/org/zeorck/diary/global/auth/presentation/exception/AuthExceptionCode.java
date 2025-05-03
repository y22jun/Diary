package org.zeorck.diary.global.auth.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.zeorck.diary.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
    REFRESH_TOKEN_NOT_VALID(UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

}
