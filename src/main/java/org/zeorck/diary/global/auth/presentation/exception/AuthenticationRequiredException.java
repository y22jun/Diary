package org.zeorck.diary.global.auth.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class AuthenticationRequiredException extends CustomException {
    public AuthenticationRequiredException() {
        super(AuthExceptionCode.AUTHENTICATION_REQUIRED);
    }
}
