package org.zeorck.diary.domain.diary.presentation.exception;

import org.zeorck.diary.global.exception.CustomException;

public class DiaryNotPublic extends CustomException {
    public DiaryNotPublic() {
        super(DiaryExceptionCode.DIARY_NOT_PUBLIC);
    }
}
