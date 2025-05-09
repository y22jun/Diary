package org.zeorck.diary.domain.diary.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class DiaryNotFoundException extends CustomException {
    public DiaryNotFoundException() {
        super(DiaryExceptionCode.DIARY_NOT_FOUND);
    }
}
