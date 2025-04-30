package org.zeorck.diary.domain.member.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.zeorck.diary.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {
    EMAIL_ALREADY_EXISTS(CONFLICT, "이메일이 이미 존재합니다.")
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

}
