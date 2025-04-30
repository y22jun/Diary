package org.zeorck.diary.domain.member.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException() {
        super(MemberExceptionCode.EMAIL_ALREADY_EXISTS);
    }
}
