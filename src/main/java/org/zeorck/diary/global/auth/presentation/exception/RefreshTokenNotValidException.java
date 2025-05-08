package org.zeorck.diary.global.auth.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class RefreshTokenNotValidException extends CustomException {
    public RefreshTokenNotValidException() {
        super(AuthExceptionCode.REFRESH_TOKEN_NOT_VALID);
    }
}
