package org.zeorck.diary.domain.member.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class MemberNotFoundException extends CustomException {
    public MemberNotFoundException() {
        super(MemberExceptionCode.MEMBER_NOT_FOUND);
    }
}
