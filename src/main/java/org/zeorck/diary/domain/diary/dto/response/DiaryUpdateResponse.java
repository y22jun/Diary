package org.zeorck.diary.domain.diary.dto.response;

import lombok.Builder;

@Builder
public record DiaryUpdateResponse(

        Long diaryId

) {
}
