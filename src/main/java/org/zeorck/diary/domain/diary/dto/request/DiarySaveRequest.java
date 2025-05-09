package org.zeorck.diary.domain.diary.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DiarySaveRequest(

        @NotNull(message = "제목을 입력해 주세요.")
        String title,

        @NotNull(message = "내용을 입력해 주세요.")
        String content

) {
}
