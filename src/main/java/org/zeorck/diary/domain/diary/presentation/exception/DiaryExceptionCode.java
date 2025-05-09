package org.zeorck.diary.domain.diary.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.zeorck.diary.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum DiaryExceptionCode implements ExceptionCode {
    DIARY_NOT_FOUND(NOT_FOUND, "일기를 찾을 수 없습니다."),
    DIARY_NOT_FORBIDDEN(FORBIDDEN, "해당 일기에 접근할 권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
