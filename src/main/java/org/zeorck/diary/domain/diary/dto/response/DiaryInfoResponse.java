package org.zeorck.diary.domain.diary.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DiaryInfoResponse(

        Long diaryId,

        Long memberId,

        String title,

        String content,

        LocalDateTime createdAt

) {
}
