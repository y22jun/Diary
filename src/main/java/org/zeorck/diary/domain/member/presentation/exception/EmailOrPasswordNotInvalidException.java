package org.zeorck.diary.domain.member.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class EmailOrPasswordNotInvalidException extends CustomException {
    public EmailOrPasswordNotInvalidException() {
        super(MemberExceptionCode.EMAIL_OR_PASSWORD_NOT_INVALID);
    }
}
