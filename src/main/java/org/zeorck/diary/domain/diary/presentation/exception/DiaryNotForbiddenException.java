package org.zeorck.diary.domain.diary.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class DiaryNotForbiddenException extends CustomException {
    public DiaryNotForbiddenException() {
        super(DiaryExceptionCode.DIARY_NOT_FORBIDDEN);
    }
}
